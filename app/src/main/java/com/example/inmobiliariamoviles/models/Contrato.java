package com.example.inmobiliariamoviles.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Contrato implements Serializable {

    private int id_Contrato, id_Inmueble, id_Inqulino;
    private Inmueble inmueble;
    private Inquilino inquilino;
    private LocalDateTime fecha_Inicio, fecha_Fin;
    private double monto_Mensual;
    private boolean activo;

    public Contrato() {}

    public Contrato(int id_Contrato, int id_Inmueble, int id_Inqulino, Inmueble inmueble, Inquilino inquilino, LocalDateTime fecha_Inicio, LocalDateTime fecha_Fin, double monto_Mensual, boolean activo) {
        this.id_Contrato = id_Contrato;
        this.id_Inmueble = id_Inmueble;
        this.id_Inqulino = id_Inqulino;
        this.inmueble = inmueble;
        this.inquilino = inquilino;
        this.fecha_Inicio = fecha_Inicio;
        this.fecha_Fin = fecha_Fin;
        this.monto_Mensual = monto_Mensual;
        this.activo = activo;
    }

    public int getId_Contrato() {
        return id_Contrato;
    }

    public void setId_Contrato(int id_Contrato) {
        this.id_Contrato = id_Contrato;
    }

    public int getId_Inmueble() {
        return id_Inmueble;
    }

    public void setId_Inmueble(int id_Inmueble) {
        this.id_Inmueble = id_Inmueble;
    }

    public int getId_Inqulino() {
        return id_Inqulino;
    }

    public void setId_Inqulino(int id_Inqulino) {
        this.id_Inqulino = id_Inqulino;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public LocalDateTime getFecha_Inicio() {
        return fecha_Inicio;
    }

    public void setFecha_Inicio(LocalDateTime fecha_Inicio) {
        this.fecha_Inicio = fecha_Inicio;
    }

    public LocalDateTime getFecha_Fin() {
        return fecha_Fin;
    }

    public void setFecha_Fin(LocalDateTime fecha_Fin) {
        this.fecha_Fin = fecha_Fin;
    }

    public double getMonto_Mensual() {
        return monto_Mensual;
    }

    public void setMonto_Mensual(double monto_Mensual) {
        this.monto_Mensual = monto_Mensual;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contrato contrato = (Contrato) o;
        return id_Contrato == contrato.id_Contrato;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_Contrato);
    }
}
