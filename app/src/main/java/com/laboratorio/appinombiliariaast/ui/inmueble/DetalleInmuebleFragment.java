package com.laboratorio.appinombiliariaast.ui.inmueble;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laboratorio.appinombiliariaast.R;
import com.laboratorio.appinombiliariaast.databinding.FragmentDetalleInmuebleBinding;
import com.laboratorio.appinombiliariaast.models.InmuebleDetalleViewModel;

public class DetalleInmuebleFragment extends Fragment {

    private FragmentDetalleInmuebleBinding binding;
    private DetalleInmuebleViewModel detalleInmuebleViewModel;
    private int inmuebleId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        detalleInmuebleViewModel = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);

        inmuebleId = getArguments().getInt("inmueble_id");
        detalleInmuebleViewModel.cargarDetalle(inmuebleId);

        // Observar el LiveData desde el ViewModel
        detalleInmuebleViewModel.getDetalle().observe(getViewLifecycleOwner(), new Observer<InmuebleDetalleViewModel>() {
                    @Override
                    public void onChanged(InmuebleDetalleViewModel inmuebleDetalleViewModel) {
                        binding.tvDireccion.setText("Dirección: " + inmuebleDetalleViewModel.getDireccion());
                        binding.tvUso.setText("Uso: " + inmuebleDetalleViewModel.getUso());
                        binding.tvTipo.setText("Tipo: " + inmuebleDetalleViewModel.getTipo());
                        binding.tvAmbientes.setText("Cantidad de Ambientes: " + inmuebleDetalleViewModel.getCantidad_Ambientes());
                        binding.tvPrecio.setText("Precio: $" + inmuebleDetalleViewModel.getPrecio());
                        binding.tvDisponibilidad.setText("Disponibilidad: " + (inmuebleDetalleViewModel.isDisponibilidad() ? "Disponible" : "No disponible"));
                        binding.tvLatitud.setText("Latitud: " + inmuebleDetalleViewModel.getLatitud());
                        binding.tvLongitud.setText("Longitud: " + inmuebleDetalleViewModel.getLongitud());
                        String baseUrl = "http://192.168.1.2:5166";
                        String FotoPath = inmuebleDetalleViewModel.getFoto();
                        String FotoUrl = baseUrl + FotoPath;
                        Glide.with(requireContext())
                                .load(FotoUrl)
                                .placeholder(R.drawable.loading_plc)
                                .error(R.drawable.ic_launcher_foreground)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(binding.ivFoto);


                        // Desactivar temporalmente el listener
                        binding.cbDisponibilidad.setOnCheckedChangeListener(null);
                        binding.cbDisponibilidad.setChecked(inmuebleDetalleViewModel.isDisponibilidad());

                        // Volver a activar el listener después de configurar el estado inicial
                        binding.cbDisponibilidad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                                detalleInmuebleViewModel.actualizarDisponibilidad(inmuebleId, isChecked);
                            }
                        });
                    }
                });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Liberar el binding
        binding = null;
    }
}
