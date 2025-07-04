package com.example.ta_avance.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ta_avance.R;
import com.example.ta_avance.dto.reserva.DtoReserva;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder> {

    private final List<DtoReserva> listaReservas;
    private final OnReservaClickListener listener;
    private String estadoActual;

    public interface OnReservaClickListener {
        void onVerDetallesClick(DtoReserva reserva);
        void onReservaRealizadaClick(DtoReserva reserva);
    }

    public ReservaAdapter(List<DtoReserva> listaReservas, OnReservaClickListener listener, String estadoActual) {
        this.listaReservas = listaReservas;
        this.listener = listener;
        this.estadoActual = estadoActual;
    }

    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva, parent, false);
        return new ReservaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaViewHolder holder, int position) {
        DtoReserva reserva = listaReservas.get(position);

        holder.tvUsuario.setText("Usuario: " + reserva.getUsuarioNombre());
        holder.tvBarbero.setText("Barbero: " + reserva.getBarberoNombre());
        holder.tvHorario.setText("Horario: " + reserva.getHorarioRango());
        holder.tvServicio.setText("Servicio: " + reserva.getServicioNombre());

        if ("CREADA".equals(estadoActual)) {
            holder.btnVerDetalles.setVisibility(View.VISIBLE);
            holder.btnEstadoARealizada.setVisibility(View.GONE);
            holder.tvFecha.setVisibility(View.GONE);

            holder.btnVerDetalles.setOnClickListener(v -> listener.onVerDetallesClick(reserva));

        } else if ("CONFIRMADA".equals(estadoActual)) {
            holder.btnVerDetalles.setVisibility(View.GONE);
            holder.btnEstadoARealizada.setVisibility(View.VISIBLE);
            holder.tvFecha.setVisibility(View.GONE);

            holder.btnEstadoARealizada.setOnClickListener(v -> listener.onReservaRealizadaClick(reserva));

        } else if ("REALIZADA".equals(estadoActual) || "CANCELADA".equals(estadoActual)) {
            holder.btnVerDetalles.setVisibility(View.GONE);
            holder.btnEstadoARealizada.setVisibility(View.GONE);
            holder.tvFecha.setVisibility(View.VISIBLE);
            holder.tvFecha.setText("Fecha: " + reserva.getFechaReserva());
        }
    }

    @Override
    public int getItemCount() {
        return listaReservas.size();
    }

    public static class ReservaViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsuario, tvBarbero, tvHorario, tvServicio,tvFecha;
        MaterialButton btnVerDetalles,btnEstadoARealizada;

        public ReservaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsuario = itemView.findViewById(R.id.tvUsuario);
            tvBarbero = itemView.findViewById(R.id.tvBarbero);
            tvHorario = itemView.findViewById(R.id.tvHorario);
            tvServicio = itemView.findViewById(R.id.tvServicio);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            btnVerDetalles = itemView.findViewById(R.id.btnVerDetallesReserva);
            btnEstadoARealizada = itemView.findViewById(R.id.btnEstadoARealizada);
        }
    }


}
