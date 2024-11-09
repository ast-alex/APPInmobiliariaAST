package com.laboratorio.appinombiliariaast.ui.inquilino;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laboratorio.appinombiliariaast.R;
import com.laboratorio.appinombiliariaast.databinding.FragmentInquilinoBinding;
import com.laboratorio.appinombiliariaast.models.Inmueble;
import com.laboratorio.appinombiliariaast.ui.adapters.InquilinoAdapter;

import java.util.List;

public class InquilinoFragment extends Fragment {

    private InquilinoViewModel vm;
    private FragmentInquilinoBinding binding;
    private InquilinoAdapter adapter;

    public static InquilinoFragment newInstance() {
        return new InquilinoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(InquilinoViewModel.class);
        binding = FragmentInquilinoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        adapter = new InquilinoAdapter(getContext(), new InquilinoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int inmuebleId) {
                // Aquí manejas el clic, por ejemplo, mostrando el detalle del inquilino
                //detalleInquilinoFragment = new DetalleInquilinoFragment();
                // bundle = new Bundle();
                // bundle.putInt("inquilino_id", inquilinoId);
                // detalleInquilinoFragment.setArguments(bundle);
                // NavController navController = NavHostFragment.findNavController(InquilinoFragment.this);
                // navController.navigate(R.id.detalleInquilinoFragment, bundle);
            }
        });

        binding.rvInquilinos.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvInquilinos.setAdapter(adapter);

        vm.getInmuebles().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                adapter.setInmuebleList(inmuebles);
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