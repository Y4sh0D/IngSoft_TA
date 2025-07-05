package com.example.ta_avance.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ta_avance.R;
import com.example.ta_avance.dto.valoracion.ValoracionDto;

import java.util.List;

public class ValoracionAdapter extends RecyclerView.Adapter<ValoracionAdapter.ValoracionViewHolder> {

    public interface OnValoracionClickListener{
        void onMessageWsp(ValoracionDto valoracion);
    }

    private final List<ValoracionDto> valoraciones;
    private final OnValoracionClickListener listener;

    public ValoracionAdapter(List<ValoracionDto> valoraciones, OnValoracionClickListener listener) {
        this.valoraciones = valoraciones;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ValoracionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_valoracion,parent,false);
        return new ValoracionViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ValoracionViewHolder holder, int position) {
        ValoracionDto valoracion = valoraciones.get(position);
        holder.tvNombreCliente.setText("Usuario: " + valoracion.getUsuario_nombre());
        holder.tvValoracion.setText("Valoración: " + valoracion.getValoracion());
        holder.tvMensaje.setText("Mensaje: " + valoracion.getMensaje());
        holder.btnAgradecerValoracion.setOnClickListener(V -> listener.onMessageWsp(valoracion));
    }

    @Override
    public int getItemCount() {
        return valoraciones.size();
    }

    public static class ValoracionViewHolder extends RecyclerView.ViewHolder{
        TextView tvNombreCliente, tvValoracion, tvMensaje;
        Button btnAgradecerValoracion;
        public ValoracionViewHolder(@NonNull View itemView){
            super(itemView);
            tvNombreCliente = itemView.findViewById(R.id.tvNombreCliente);
            tvValoracion = itemView.findViewById(R.id.tvValoracion);
            tvMensaje = itemView.findViewById(R.id.tvMensaje);
            btnAgradecerValoracion = itemView.findViewById(R.id.btnAgradecerValoracion);
        }
    }
}
