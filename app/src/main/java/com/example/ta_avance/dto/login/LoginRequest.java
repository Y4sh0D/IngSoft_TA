package com.example.ta_avance.dto.login;

public class LoginRequest {
    private String username;
    private String password;
    private String nombre;
    private String apellido;
    private String email;
    private String celular;
    private String urlUsuario;

    public LoginRequest(String username, String password, String nombre, String apellido, String email , String celular) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email  = email;
        this.celular = celular;
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters y Setters (opcional si quieres usarlos después)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
            this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getUrlUsuario() { return urlUsuario; }

    public void setUrlUsuario(String urlUsuario) { this.urlUsuario = urlUsuario; }
}
