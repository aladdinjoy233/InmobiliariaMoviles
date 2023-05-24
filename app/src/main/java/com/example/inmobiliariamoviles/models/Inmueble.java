package com.example.inmobiliariamoviles.models;

import java.io.Serializable;
import java.util.Objects;

public class Inmueble implements Serializable {

    private int id_Inmueble, id_Propietario, ambientes;
    private Propietario propietario;
    private String direccion, usoNombre, tipoNombre, imagen;
    private double latitud, longitud, precio;
    private boolean activo = true;

    public Inmueble() {}

    public Inmueble(int id_Inmueble, int id_Propietario, int ambientes, Propietario propietario, String direccion, String usoNombre, String tipoNombre, String imagen, double latitud, double longitud, double precio, boolean activo) {
        this.id_Inmueble = id_Inmueble;
        this.id_Propietario = id_Propietario;
        this.ambientes = ambientes;
        this.propietario = propietario;
        this.direccion = direccion;
        this.usoNombre = usoNombre;
        this.tipoNombre = tipoNombre;
        this.imagen = imagen;
        this.latitud = latitud;
        this.longitud = longitud;
        this.precio = precio;
        this.activo = activo;
    }

    //    region Getters and setters
    public int getId_Inmueble() {
        return id_Inmueble;
    }

    public void setId_Inmueble(int id_Inmueble) {
        this.id_Inmueble = id_Inmueble;
    }

    public int getId_Propietario() {
        return id_Propietario;
    }

    public void setId_Propietario(int id_Propietario) {
        this.id_Propietario = id_Propietario;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUsoNombre() {
        return usoNombre;
    }

    public void setUsoNombre(String usoNombre) {
        this.usoNombre = usoNombre;
    }

    public String getTipoNombre() {
        return tipoNombre;
    }

    public void setTipoNombre(String tipoNombre) {
        this.tipoNombre = tipoNombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    //    endregion

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inmueble inmueble = (Inmueble) o;
        return id_Inmueble == inmueble.id_Inmueble;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_Inmueble);
    }

    @Override
    public String toString() {
        return "Inmueble{" +
                "id_Inmueble=" + id_Inmueble +
                ", id_Propietario=" + id_Propietario +
                ", ambientes=" + ambientes +
                ", propietario=" + propietario +
                ", direccion='" + direccion + '\'' +
                ", usoNombre='" + usoNombre + '\'' +
                ", tipoNombre='" + tipoNombre + '\'' +
                ", imagen='" + imagen + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", precio=" + precio +
                ", activo=" + activo +
                '}';
    }
}
