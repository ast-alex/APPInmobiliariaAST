package com.laboratorio.appinombiliariaast.ui.inmueble;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.laboratorio.appinombiliariaast.R;
import com.laboratorio.appinombiliariaast.databinding.FragmentCrearInmuebleBinding;

import okhttp3.MultipartBody;

public class CrearInmuebleFragment extends Fragment {
    private CrearInmuebleViewModel vm;
    private FragmentCrearInmuebleBinding binding;
    private Intent Intent;
    private ActivityResultLauncher<Intent> arl;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCrearInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        vm=new ViewModelProvider(this).get(CrearInmuebleViewModel.class);

        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> vm.recibirFoto(result));

        vm.getUriFoto().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.ivFoto.setImageURI(uri);
            }
        });

        vm.getMsj().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnSeleccionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirGaleria();
            }
        });

        binding.btnCrearInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearInmueble();
            }
        });
        return root;
    }

    private void crearInmueble() {
        String direccion = binding.etDireccion.getText().toString();
        String uso = binding.etUso.getText().toString();
        String tipo = binding.etTipo.getText().toString();
        String cantidadAmbientes = binding.etAmbientes.getText().toString();
        String latitud = binding.etLatitud.getText().toString();
        String longitud = binding.etLongitud.getText().toString();
        String precio = binding.etPrecio.getText().toString();

        vm.crearInmueble(direccion, uso, tipo, cantidadAmbientes, latitud, longitud, precio);

        binding.etDireccion.setText("");
        binding.etUso.setText("");
        binding.etTipo.setText("");
        binding.etAmbientes.setText("");
        binding.etLatitud.setText("");
        binding.etLongitud.setText("");
        binding.etPrecio.setText("");
        binding.ivFoto.setImageURI(null);
    }

    private void abrirGaleria() {
        Intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                arl.launch(Intent);
    };
}