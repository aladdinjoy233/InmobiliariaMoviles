package com.example.inmobiliariamoviles.models;

public class Usuario {

    private String correo;
    private String password;

    public Usuario(String usuario, String password) {
        this.correo = usuario;
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
