package com.example.ta_avance.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ta_avance.R;
import com.example.ta_avance.dto.Barbero;

import java.util.List;

public class BarberoAdapter extends RecyclerView.Adapter<BarberoAdapter.BarberoViewHolder> {

    public interface OnBarberoClickListener {
        void onActualizar(Barbero barbero);
        void onEliminar(Barbero barbero);
    }

    private final List<Barbero> barberos;
    private final OnBarberoClickListener listener;

    public BarberoAdapter(List<Barbero> barberos, OnBarberoClickListener listener) {
        this.barberos = barberos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BarberoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barbero, parent, false);
        return new BarberoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull BarberoViewHolder holder, int position) {
        Barbero barbero = barberos.get(position);
        holder.textNombre.setText(barbero.getNombre());
        holder.textEstado.setText("Estado: " + (barbero.getEstado() == 0 ? "Activo" : "Inactivo"));

        holder.btnActualizar.setOnClickListener(v -> listener.onActualizar(barbero));
        holder.btnEliminar.setOnClickListener(v -> listener.onEliminar(barbero));
    }

    @Override
    public int getItemCount() {
        return barberos.size();
    }

    public static class BarberoViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre, textEstado;
        Button btnActualizar, btnEliminar;

        public BarberoViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.textNombre);
            textEstado = itemView.findViewById(R.id.textEstado);
            btnActualizar = itemView.findViewById(R.id.btnActualizar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
