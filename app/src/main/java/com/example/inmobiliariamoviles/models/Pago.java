package com.example.inmobiliariamoviles.models;

import java.io.Serializable;

public class Pago implements Serializable {

    private int id_Pago, id_Contrato, numero;
    private Contrato contrato;
    private String fecha;
    private double importe;

    public Pago() {}

    public Pago(int id_Pago, int id_Contrato, int numero, Contrato contrato, String fecha, double importe) {
        this.id_Pago = id_Pago;
        this.id_Contrato = id_Contrato;
        this.contrato = contrato;
        this.numero = numero;
        this.fecha = fecha;
        this.importe = importe;
    }

    public int getId_Pago() {
        return id_Pago;
    }

    public void setId_Pago(int id_Pago) {
        this.id_Pago = id_Pago;
    }

    public int getId_Contrato() {
        return id_Contrato;
    }

    public void setId_Contrato(int id_Contrato) {
        this.id_Contrato = id_Contrato;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }
}
