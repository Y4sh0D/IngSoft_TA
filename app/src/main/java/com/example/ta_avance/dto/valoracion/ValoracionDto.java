package com.example.ta_avance.dto.valoracion;

public class ValoracionDto {
    private long valoracion_id;
    private long usuarioId;
    private String celular;
    private long valoracion;
    private boolean util;
    private String mensaje;
    private String usuario_nombre;

    public long getValoracion_id() {
        return valoracion_id;
    }

    public void setValoracion_id(long valoracion_id) {
        this.valoracion_id = valoracion_id;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public long getValoracion() {
        return valoracion;
    }

    public void setValoracion(long valoracion) {
        this.valoracion = valoracion;
    }

    public boolean isUtil() {
        return util;
    }

    public void setUtil(boolean util) {
        this.util = util;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getUsuario_nombre() {
        return usuario_nombre;
    }

    public void setUsuario_nombre(String usuario_nombre) {
        this.usuario_nombre = usuario_nombre;
    }
}
