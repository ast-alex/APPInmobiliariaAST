package com.laboratorio.appinombiliariaast.models;

public class InquilinoResponse {
    private Inquilino inquilino;
    private String inmuebleDireccion;
    private String inmuebleFoto;

    // Getters y Setters
    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
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
}
