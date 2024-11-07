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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

        // Crear el Adapter y pasar el listener
        adapter = new InmuebleAdapter(getContext(), new InmuebleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int inmuebleId) {
                // Aquí manejas el clic, por ejemplo, mostrando el detalle del inmueble
                DetalleInmuebleFragment detalleFragment = new DetalleInmuebleFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("inmueble_id", inmuebleId);
                detalleFragment.setArguments(bundle);

                NavController navController = NavHostFragment.findNavController(InmuebleFragment.this);
                navController.navigate(R.id.detalleFragment, bundle);
            }
        });
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

        //btn flotante hacia crear inmueble
        binding.fabAddInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = NavHostFragment.findNavController(InmuebleFragment.this);
                navController.navigate(R.id.nav_crear_inmueble);
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
