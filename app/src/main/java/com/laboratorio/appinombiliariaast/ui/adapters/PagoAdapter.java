package com.laboratorio.appinombiliariaast.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laboratorio.appinombiliariaast.R;
import com.laboratorio.appinombiliariaast.models.Pago;

import java.util.List;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.PagoViewHolder> {

    private List<Pago> pagosList;
    private Context context;

    public PagoAdapter(Context context) {
        this.context = context;
    }

    public void setPagosList(List<Pago> pagosList) {
        this.pagosList = pagosList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PagoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pago, parent, false);
        return new PagoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PagoViewHolder holder, int position) {
        Pago pago = pagosList.get(position);
        holder.tvNumeroPago.setText("Número de Pago: " + pago.getNumero_pago());
        holder.tvFechaPago.setText("Fecha de Pago: " + pago.getFecha_pago());
        holder.tvImporte.setText("Importe: $" + pago.getImporte());
        holder.tvConcepto.setText("Concepto: " + pago.getConcepto());
        holder.tvEstado.setText("Estado: " + (pago.isEstado() ? "Activo" : "Inactivo"));
    }

    @Override
    public int getItemCount() {
        return (pagosList != null) ? pagosList.size() : 0;
    }

    public class PagoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumeroPago, tvFechaPago, tvImporte, tvConcepto, tvEstado;

        public PagoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumeroPago = itemView.findViewById(R.id.tvNumeroPago);
            tvFechaPago = itemView.findViewById(R.id.tvFechaPago);
            tvImporte = itemView.findViewById(R.id.tvImporte);
            tvConcepto = itemView.findViewById(R.id.tvConcepto);
            tvEstado = itemView.findViewById(R.id.tvEstado);
        }
    }
}
