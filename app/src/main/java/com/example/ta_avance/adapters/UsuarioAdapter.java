package com.example.ta_avance.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ta_avance.R;
import com.example.ta_avance.dto.login.LoginRequest;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    public interface OnUsuarioClickListener {
        void onMessageWsp(LoginRequest usuario);
    }

    private final List<LoginRequest> usuarios;
    private final OnUsuarioClickListener listener;



    public UsuarioAdapter(List<LoginRequest> usuarios, OnUsuarioClickListener listener){
        this.usuarios = usuarios;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario,parent,false);
        return new UsuarioViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        LoginRequest usuario = usuarios.get(position);
        holder.textNombreCompleto.setText(usuario.getNombre() + " " + usuario.getApellido());
        holder.textNumero.setText(usuario.getCelular());
        holder.textEmail.setText(usuario.getEmail());
        holder.btnEnviarWspUsuarioCreado.setOnClickListener(V -> listener.onMessageWsp(usuario));
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder{
        TextView textNombreCompleto, textNumero, textEmail;
        Button btnEnviarWspUsuarioCreado;
        ImageView ivFotoUsuario;
        public UsuarioViewHolder(@NonNull View itemView){
            super(itemView);
            textNombreCompleto = itemView.findViewById(R.id.textNombreCompleto);
            textNumero = itemView.findViewById(R.id.textNumero);
            textEmail = itemView.findViewById(R.id.textEmail);
            btnEnviarWspUsuarioCreado = itemView.findViewById(R.id.btnEnviarWspUsuarioCreado);
            ivFotoUsuario = itemView.findViewById(R.id.ivFotoUsuario);
        }
    }
}
