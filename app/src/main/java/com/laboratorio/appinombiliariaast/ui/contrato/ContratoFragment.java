package com.laboratorio.appinombiliariaast.ui.contrato;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.laboratorio.appinombiliariaast.R;
import com.laboratorio.appinombiliariaast.databinding.FragmentContratoBinding;
import com.laboratorio.appinombiliariaast.models.Contrato;
import com.laboratorio.appinombiliariaast.ui.adapters.ContratoAdapter;

import java.util.List;

public class ContratoFragment extends Fragment {

    private ContratoViewModel vm;
    private FragmentContratoBinding binding;
    private ContratoAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        vm = new ViewModelProvider(this).get(ContratoViewModel.class);
        binding = FragmentContratoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configurar el Adapter para Contratos y manejar el clic en cada contrato
        adapter = new ContratoAdapter(getContext(), new ContratoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int contratoId) {
                // Aquí manejamos el clic, por ejemplo, navegando a la vista de detalle del contrato
                Bundle bundle = new Bundle();
                bundle.putInt("contrato_id", contratoId);

                NavController navController = NavHostFragment.findNavController(ContratoFragment.this);
                navController.navigate(R.id.detalleContratoFragment, bundle);
            }
        });
        binding.rvInmuebles.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvInmuebles.setAdapter(adapter);

        // Observa los cambios en la lista de contratos
        vm.getContratos().observe(getViewLifecycleOwner(), new Observer<List<Contrato>>() {
            @Override
            public void onChanged(List<Contrato> contratos) {
                adapter.setContratosList(contratos);
                Log.d("ContratoFragment", "Contratos cargados en la UI: " + contratos.size());
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
