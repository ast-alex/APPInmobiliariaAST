package com.laboratorio.appinombiliariaast.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.laboratorio.appinombiliariaast.R;
import com.laboratorio.appinombiliariaast.models.Contrato;

import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ContratoViewHolder> {

    private List<Contrato> contratosList;
    private Context context;
    private OnItemClickListener onItemClickListener; // Listener para manejar el clic

    public ContratoAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void setContratosList(List<Contrato> contratosList) {
        this.contratosList = contratosList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contrato, parent, false);
        return new ContratoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratoAdapter.ContratoViewHolder holder, int position) {
        Contrato contrato = contratosList.get(position);
        holder.tvDireccion.setText(contrato.getInmuebleDireccion());

        String baseUrl = "http://192.168.1.2:5166";  // Base URL de las fotos en tu servidor
        String fotoPath = contrato.getInmuebleFoto();
        String fotoUrl = baseUrl + fotoPath;

        // Cargar la foto con Glide
        Glide.with(context)
                .load(fotoUrl)
                .placeholder(R.drawable.loading_plc)
                .error(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("ContratoAdapter", "Error al cargar la foto: ", e);
                        return false; // Muestra el error de imagen por defecto
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d("ContratoAdapter", "Foto cargada correctamente.");
                        return false;
                    }
                })
                .into(holder.ivInmueble);

        // Configurar clic en "Ver Contrato"
        holder.tvVer.setOnClickListener(v -> {
            int contratoId = contrato.getiD_contrato();
            onItemClickListener.onItemClick(contratoId);
        });
    }

    @Override
    public int getItemCount() {
        return (contratosList != null) ? contratosList.size() : 0;
    }

    public class ContratoViewHolder extends RecyclerView.ViewHolder {
        TextView tvDireccion, tvVer;
        ImageView ivInmueble;

        public ContratoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvVer = itemView.findViewById(R.id.tvVer);
            ivInmueble = itemView.findViewById(R.id.ivInmueble);
        }
    }

    // Interface personalizada para manejar el clic
    public interface OnItemClickListener {
        void onItemClick(int contratoId);
    }
}
