package com.laboratorio.appinombiliariaast.models;

public class InmuebleDetalleViewModel {
    private int iD_inmueble;
    private String direccion;
    private String uso;
    private String tipo;
    private int cantidad_Ambientes;
    private Double latitud;
    private Double longitud;
    private Double precio;
    private boolean disponibilidad;
    private String foto;

    public InmuebleDetalleViewModel() {
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getiD_inmueble() {
        return iD_inmueble;
    }

    public void setiD_inmueble(int iD_inmueble) {
        this.iD_inmueble = iD_inmueble;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad_Ambientes() {
        return cantidad_Ambientes;
    }

    public void setCantidad_Ambientes(int cantidad_Ambientes) {
        this.cantidad_Ambientes = cantidad_Ambientes;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
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

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
