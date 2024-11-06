package com.laboratorio.appinombiliariaast.models;

public class Inmueble {
    private int iD_inmueble;
    private String direccion;
    private int uso;
    private int tipo;
    private int cantidad_Ambientes;
    private Double latitud;
    private Double longitud;
    private Double precio;
    private boolean estado;
    private boolean disponibilidad;
    private int iD_propietario;
    private Propietario propietario;
    private String foto;

    public Inmueble() {
    }

    public int getiD_inmueble() {
        return iD_inmueble;
    }

    public void setiD_inmueble(int iD_inmueble) {
        this.iD_inmueble = iD_inmueble;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getUso() {
        return uso;
    }

    public void setUso(int uso) {
        this.uso = uso;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public int getCantidad_Ambientes() {
        return cantidad_Ambientes;
    }

    public void setCantidad_Ambientes(int cantidad_Ambientes) {
        this.cantidad_Ambientes = cantidad_Ambientes;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public int getiD_propietario() {
        return iD_propietario;
    }

    public void setiD_propietario(int iD_propietario) {
        this.iD_propietario = iD_propietario;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
