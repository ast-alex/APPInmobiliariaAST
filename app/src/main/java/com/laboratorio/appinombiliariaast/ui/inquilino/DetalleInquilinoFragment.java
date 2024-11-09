package com.laboratorio.appinombiliariaast.ui.inquilino;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laboratorio.appinombiliariaast.R;
import com.laboratorio.appinombiliariaast.databinding.FragmentDetalleInquilinoBinding;
import com.laboratorio.appinombiliariaast.models.Inquilino;

public class DetalleInquilinoFragment extends Fragment {

    private DetalleInquilinoViewModel vm;
    private FragmentDetalleInquilinoBinding binding;
    private int inmuebleId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);
        binding = FragmentDetalleInquilinoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        inmuebleId = getArguments().getInt("inmueble_id");
        vm.cargarDetalle(inmuebleId);

        vm.getInquilinoDetalle().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                binding.tvTitulo.setText("Datos del Inquilino");
                binding.tvDni.setText("DNI: " + inquilino.getDni());
                binding.tvApellido.setText("Apellido: " + inquilino.getApellido());
                binding.tvNombre.setText("Nombre: " + inquilino.getNombre());
                binding.tvTelefono.setText("Telefono: " + inquilino.getTelefono());
                binding.tvEmail.setText("Email: " + inquilino.getEmail());
                binding.tvDireccion.setText("Dirección: " + inquilino.getDireccion());
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}