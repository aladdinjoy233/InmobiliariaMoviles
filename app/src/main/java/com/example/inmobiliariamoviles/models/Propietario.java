package com.example.inmobiliariamoviles.models;

import java.util.Objects;

public class Propietario {

    public int id_Propietario;
    public String dni, nombre, apellido, correo, telefono;

    public Propietario(){}

    public Propietario(int id, String dni, String nombre, String apellido, String correo, String telefono) {
        this.id_Propietario = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
    }

    public int getId_Propietario() {
        return id_Propietario;
    }

    public void setId_Propietario(int id_Propietario) {
        this.id_Propietario = id_Propietario;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Propietario that = (Propietario) o;
        return id_Propietario == that.id_Propietario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_Propietario);
    }

    public String obtenerNombreCompleto() {
        return nombre + " " + apellido;
    }
}
