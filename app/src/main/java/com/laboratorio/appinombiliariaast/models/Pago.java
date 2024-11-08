package com.laboratorio.appinombiliariaast.models;

public class Pago {
    private int iD_pago;
    private int iD_contrato;
    private int numero_pago;
    private String fecha_pago;
    private int importe;
    private boolean estado;
    private String concepto;

    public Pago() {
    }

    public int getiD_pago() {
        return iD_pago;
    }

    public void setiD_pago(int iD_pago) {
        this.iD_pago = iD_pago;
    }

    public int getiD_contrato() {
        return iD_contrato;
    }

    public void setiD_contrato(int iD_contrato) {
        this.iD_contrato = iD_contrato;
    }

    public int getNumero_pago() {
        return numero_pago;
    }

    public void setNumero_pago(int numero_pago) {
        this.numero_pago = numero_pago;
    }

    public String getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(String fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public int getImporte() {
        return importe;
    }

    public void setImporte(int importe) {
        this.importe = importe;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
}
