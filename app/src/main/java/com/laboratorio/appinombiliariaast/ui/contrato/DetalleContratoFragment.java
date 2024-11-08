package com.laboratorio.appinombiliariaast.ui.contrato;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laboratorio.appinombiliariaast.R;
import com.laboratorio.appinombiliariaast.databinding.FragmentDetalleContratoBinding;
import com.laboratorio.appinombiliariaast.models.Contrato;

public class DetalleContratoFragment extends Fragment {

    private DetalleContratoViewModel vm;
    private FragmentDetalleContratoBinding binding;
    private int contratoId;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);

        if (getArguments() != null) {
            contratoId = getArguments().getInt("contrato_id");
        }
        Log.d("DetalleContratoFragment", "Contrato ID: " + contratoId);
        vm.cargarDetalle(contratoId);



        vm.getContratoDetalle().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(Contrato contrato) {
                binding.tvDireccion.setText("Dirección: " + contrato.getInmuebleDireccion());
                binding.tvFInicio.setText("Fecha de Inicio: " + contrato.getFecha_Inicio());
                binding.tvFFin.setText("Fecha de Fin: " + contrato.getFecha_Fin());
                binding.tvPrecio.setText("Monto Mensual: $" + contrato.getMonto_Mensual());
                binding.tvInquilino.setText("Inquilino: " + contrato.getInquilinoNombreCompleto());
                String baseUrl = "http://192.168.1.2:5166";
                String FotoPath = contrato.getInmuebleFoto();
                String FotoUrl = baseUrl + FotoPath;
                Glide.with(requireContext())
                        .load(FotoUrl)
                        .placeholder(R.drawable.loading_plc)
                        .error(R.drawable.ic_launcher_foreground)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivFoto);
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