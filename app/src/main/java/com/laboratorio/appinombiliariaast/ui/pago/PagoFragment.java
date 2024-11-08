package com.laboratorio.appinombiliariaast.ui.pago;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laboratorio.appinombiliariaast.R;
import com.laboratorio.appinombiliariaast.databinding.FragmentPagoBinding;
import com.laboratorio.appinombiliariaast.models.Pago;
import com.laboratorio.appinombiliariaast.ui.adapters.PagoAdapter;

import java.util.List;

public class PagoFragment extends Fragment {

    private PagoViewModel vm;
    private FragmentPagoBinding binding;
    private PagoAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPagoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        vm = new ViewModelProvider(this).get(PagoViewModel.class);

        int idContrato = getArguments().getInt("contrato_id");
        vm.cargarPagos(idContrato);

        binding.rvPagos.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PagoAdapter(getContext());
        binding.rvPagos.setAdapter(adapter);

        vm.getPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                adapter.setPagosList(pagos);
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