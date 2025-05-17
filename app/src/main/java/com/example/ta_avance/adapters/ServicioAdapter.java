package com.example.ta_avance.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ta_avance.R;
import com.example.ta_avance.dto.servicio.ServicioDto;

import java.util.List;

public class ServicioAdapter extends RecyclerView.Adapter<ServicioAdapter.ServicioViewHolder> {

    public interface OnServicioClickListener {
        void onActualizar(ServicioDto servicio);
        void onEliminar(ServicioDto servicio);
    }

    private final List<ServicioDto> servicios;
    private final OnServicioClickListener listener;

    public ServicioAdapter(List<ServicioDto> servicios, OnServicioClickListener listener) {
        this.servicios = servicios;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ServicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicio, parent, false);
        return new ServicioViewHolder(vista);
    }

    // Dentro de ServicioAdapter.java

    @Override
    public void onBindViewHolder(@NonNull ServicioViewHolder holder, int position) {
        ServicioDto servicio = servicios.get(position);
        holder.textNombre.setText(servicio.getNombre());
        holder.textPrecio.setText("S/ " + servicio.getPrecio());
        holder.textDescripcion.setText(servicio.getDescripcion());
        holder.textTipoServicio.setText(servicio.getNombre_tipoServicio());

        holder.btnActualizar.setOnClickListener(v -> listener.onActualizar(servicio));
        holder.btnEliminar.setOnClickListener(v -> listener.onEliminar(servicio));
    }

    @Override
    public int getItemCount() {
        return servicios.size();
    }

    public static class ServicioViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre, textPrecio, textDescripcion, textTipoServicio;
        Button btnActualizar, btnEliminar;

        public ServicioViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.textNombreServicio);
            textPrecio = itemView.findViewById(R.id.textPrecioServicio);
            textDescripcion = itemView.findViewById(R.id.textDescripcionServicio);
            textTipoServicio = itemView.findViewById(R.id.textTipoServicio);
            btnActualizar = itemView.findViewById(R.id.btnActualizarServicio);
            btnEliminar = itemView.findViewById(R.id.btnEliminarServicio);
        }
    }

}
