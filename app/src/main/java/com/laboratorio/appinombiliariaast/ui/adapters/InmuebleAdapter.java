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
import com.laboratorio.appinombiliariaast.models.Inmueble;

import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.InmuebleViewHolder> {

    private List<Inmueble> inmueblesList;
    private Context context;
    private OnItemClickListener onItemClickListener;  // Listener para manejar el clic

    public InmuebleAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void setInmueblesList(List<Inmueble> inmueblesList ) {
        Log.d("InmuebleAdapter", "Inmuebles recibidos: " + inmueblesList.size());
        this.inmueblesList = inmueblesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InmuebleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inmueble, parent, false);
        return new InmuebleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleAdapter.InmuebleViewHolder holder, int position) {
        Inmueble inmueble = inmueblesList.get(position);
        holder.tvDireccion.setText(inmueble.getDireccion());
        holder.tvPrecio.setText("$" + inmueble.getPrecio());

        String baseUrl = "http://192.168.1.2:5166";
        String fotoPath = inmueble.getFoto();
        String fotoUrl = baseUrl + fotoPath;
        Log.d("InmuebleAdapter", "Foto: " + inmueble.getFoto());
        Glide.with(context)
                .load(fotoUrl)
                .placeholder(R.drawable.loading_plc)
                .error(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("PerfilFragment", "Error al cargar la foto: ", e);
                        return false; // Muestra el error de imagen por defecto
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d("PerfilFragment", "Foto cargada correctamente.");
                        return false;
                    }
                })
                .into(holder.ivInmueble);
    }

    @Override
    public int getItemCount() {
        return (inmueblesList != null) ? inmueblesList.size() : 0;
    }

    public class InmuebleViewHolder extends RecyclerView.ViewHolder {
        TextView tvDireccion, tvPrecio;
        ImageView ivInmueble;

        public InmuebleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            ivInmueble = itemView.findViewById(R.id.ivInmueble);

            // Aquí escuchamos el clic en el ítem
            itemView.setOnClickListener(v -> {
                int inmuebleId = inmueblesList.get(getAdapterPosition()).getiD_inmueble();
                onItemClickListener.onItemClick(inmuebleId);
            });
        }
    }

    // Interface personalizada para manejar el clic
    public interface OnItemClickListener {
        void onItemClick(int inmuebleId);
    }
}

