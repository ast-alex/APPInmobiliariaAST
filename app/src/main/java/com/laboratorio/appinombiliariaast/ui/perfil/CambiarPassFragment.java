package com.laboratorio.appinombiliariaast.ui.perfil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laboratorio.appinombiliariaast.R;
import com.laboratorio.appinombiliariaast.databinding.FragmentCambiarPassBinding;

public class CambiarPassFragment extends Fragment {
    private CambiarPassViewModel vm;
    private FragmentCambiarPassBinding binding;

    private CambiarPassViewModel mViewModel;

    public static CambiarPassFragment newInstance() {
        return new CambiarPassFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(CambiarPassViewModel.class);
        binding = FragmentCambiarPassBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PasswordActual = binding.etPasswordActual.getText().toString();
                String NuevaPassword = binding.etNuevaPassword.getText().toString();
                String ConfirmarPassword = binding.etConfirmarPassword.getText().toString();

                vm.cambiarPassword(PasswordActual, NuevaPassword, ConfirmarPassword);

                binding.etPasswordActual.setText("");
                binding.etNuevaPassword.setText("");
                binding.etConfirmarPassword.setText("");
            }
        });

        vm.getPasswordChangeSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                NavController navController = NavHostFragment.findNavController(CambiarPassFragment.this);
                navController.navigate(R.id.nav_perfil);
            }
        });
        return root;

    }

}