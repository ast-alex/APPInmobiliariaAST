package com.laboratorio.appinombiliariaast.ui.perfil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.laboratorio.appinombiliariaast.R;
import com.laboratorio.appinombiliariaast.databinding.FragmentPerfilBinding;
import com.laboratorio.appinombiliariaast.models.Propietario;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private PerfilViewModel vm;
    private ActivityResultLauncher<Intent> arl;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getPermisos();


        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> vm.recibirFoto(result));

        // Observo los cambios en el LiveData de Propietario
        vm.getMPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                // Actualiza la UI con los datos del propietario
                binding.etDni.setText(propietario.getDni());
                binding.etNombre.setText(propietario.getNombre());
                binding.etApellido.setText(propietario.getApellido());
                binding.etTelefono.setText(propietario.getTelefono());
                binding.etDireccion.setText(propietario.getDireccion());
                binding.etEmail.setText(propietario.getEmail());
                binding.btnSubirAvatar.setOnClickListener(view -> abrirGaleria());

                String baseUrl = "http://192.168.1.2:5166";
                String avatarPath = propietario.getAvatar();
                String avatarUrl = baseUrl + avatarPath;
                Log.d("PerfilFragment", "Avatar: " + propietario.getAvatar());
                Glide.with(getContext())
                        .load(avatarUrl)
                        .placeholder(R.drawable.loading_plc)
                        .error(R.drawable.ic_launcher_foreground)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Log.e("PerfilFragment", "Error al cargar el avatar: ", e);
                                return false; // Muestra el error de imagen por defecto
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                Log.d("PerfilFragment", "Avatar cargado correctamente.");
                                return false;
                            }
                        })
                        .into(binding.imgAvatar);
            }
        });

        vm.getIsEdit().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isEdit) {
                habilitarCampos(isEdit);
                binding.btnEditar.setText(isEdit ? "Guardar" : "Editar");

            }
        });
        vm.getUriFoto().observe(getViewLifecycleOwner(), uri -> {
            binding.imgAvatar.setImageURI(uri);
        });

        binding.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.toogleEditMode(
                    binding.etDni.getText().toString(),
                    binding.etNombre.getText().toString(),
                    binding.etApellido.getText().toString(),
                    binding.etTelefono.getText().toString(),
                    binding.etEmail.getText().toString(),
                    binding.etDireccion.getText().toString()
                );
            }
        });

        binding.btnCambiarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //navego a fragmentCambiarPass
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_cambiarpass);
            }
        });

        vm.obtenerPropietario();

        return root;
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl.launch(intent);
    }

    private void habilitarCampos(Boolean isEdit) {
        binding.etDni.setEnabled(isEdit);
        binding.etNombre.setEnabled(isEdit);
        binding.etApellido.setEnabled(isEdit);
        binding.etTelefono.setEnabled(isEdit);
        binding.etEmail.setEnabled(isEdit);
        binding.etDireccion.setEnabled(isEdit);
        binding.btnSubirAvatar.setVisibility(isEdit ? View.VISIBLE : View.GONE);
    }
    private void getPermisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, 1000);
            }
        }
    }

    private int checkSelfPermission(String writeExternalStorage) {
        return 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}