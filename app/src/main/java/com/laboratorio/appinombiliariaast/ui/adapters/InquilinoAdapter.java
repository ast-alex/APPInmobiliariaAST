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

public class InquilinoAdapter extends RecyclerView.Adapter<InquilinoAdapter.InquilinoViewHolder> {

    private List<Inmueble> inmuebleList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public InquilinoAdapter(Context context, OnItemClickListener onItemClickListener){
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void setInmuebleList(List<Inmueble> inmuebleList){
        this.inmuebleList = inmuebleList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InquilinoAdapter.InquilinoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inquilino, parent, false);
        return new InquilinoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InquilinoAdapter.InquilinoViewHolder holder, int position) {
        Inmueble inmueble = inmuebleList.get(position);
        holder.tvDireccion.setText(inmueble.getDireccion());
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
                        return false; // La imagen se ha cargado correctamente
                    }
                })
                .into(holder.ivInmueble);
    }

    @Override
    public int getItemCount() {
        return (inmuebleList != null) ? inmuebleList.size() : 0;
    }

    public class InquilinoViewHolder extends RecyclerView.ViewHolder {
        TextView tvDireccion, tvVer;
        ImageView ivInmueble;

        public InquilinoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvVer = itemView.findViewById(R.id.tvVer);
            ivInmueble = itemView.findViewById(R.id.ivInmueble);

            //configurar click en "Ver Inquilino
            itemView.setOnClickListener(v -> {
                int inmuebleId = inmuebleList.get(getAdapterPosition()).getiD_inmueble();
                onItemClickListener.onItemClick(inmuebleId);
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int inmuebleId);
    }
}
