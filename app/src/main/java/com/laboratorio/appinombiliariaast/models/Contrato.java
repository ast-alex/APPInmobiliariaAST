package com.laboratorio.appinombiliariaast.models;

public class Contrato {
    private int iD_contrato;
    private int iD_inmueble;
    private int iD_inquilino;
    private String inmuebleDireccion;
    private String inmuebleFoto;
    private String inquilinoNombreCompleto;
    private String fecha_Inicio; ;
    private String fecha_Fin;
    private double monto_Mensual;


    public Contrato() {
    }

    public int getiD_contrato() {
        return iD_contrato;
    }

    public void setiD_contrato(int iD_contrato) {
        this.iD_contrato = iD_contrato;
    }

    public int getiD_inmueble() {
        return iD_inmueble;
    }

    public void setiD_inmueble(int iD_inmueble) {
        this.iD_inmueble = iD_inmueble;
    }

    public int getiD_inquilino() {
        return iD_inquilino;
    }

    public void setiD_inquilino(int iD_inquilino) {
        this.iD_inquilino = iD_inquilino;
    }

    public String getInmuebleDireccion() {
        return inmuebleDireccion;
    }

    public void setInmuebleDireccion(String inmuebleDireccion) {
        this.inmuebleDireccion = inmuebleDireccion;
    }

    public String getInmuebleFoto() {
        return inmuebleFoto;
    }

    public void setInmuebleFoto(String inmuebleFoto) {
        this.inmuebleFoto = inmuebleFoto;
    }

    public String getFecha_Inicio() {
        return fecha_Inicio;
    }

    public void setFecha_Inicio(String fecha_Inicio) {
        this.fecha_Inicio = fecha_Inicio;
    }

    public String getFecha_Fin() {
        return fecha_Fin;
    }

    public void setFecha_Fin(String fecha_Fin) {
        this.fecha_Fin = fecha_Fin;
    }

    public double getMonto_Mensual() {
        return monto_Mensual;
    }

    public void setMonto_Mensual(double monto_Mensual) {
        this.monto_Mensual = monto_Mensual;
    }

    public String getInquilinoNombreCompleto() {
        return inquilinoNombreCompleto;
    }

    public void setInquilinoNombreCompleto(String inquilinoNombreCompleto) {
        this.inquilinoNombreCompleto = inquilinoNombreCompleto;
    }
}
