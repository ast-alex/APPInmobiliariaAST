package com.laboratorio.appinombiliariaast.ui.inmueble;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.laboratorio.appinombiliariaast.R;
import com.laboratorio.appinombiliariaast.databinding.FragmentInmuebleBinding;
import com.laboratorio.appinombiliariaast.ui.adapters.InmuebleAdapter;
import com.laboratorio.appinombiliariaast.models.Inmueble;


import java.util.List;

public class InmuebleFragment extends Fragment {

    private InmuebleViewModel vm;
    private FragmentInmuebleBinding binding;
    private InmuebleAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        vm = new ViewModelProvider(this).get(InmuebleViewModel.class);
        binding = FragmentInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        adapter = new InmuebleAdapter(getContext());
        binding.rvInmuebles.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvInmuebles.setAdapter(adapter);

        // Observa los cambios en la lista de inmuebles
        vm.getInmuebles().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                adapter.setInmueblesList(inmuebles);
                Log.d("InmuebleFragment", "Inmuebles cargados en la UI: " + inmuebles.size());
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
