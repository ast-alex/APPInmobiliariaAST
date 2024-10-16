package com.laboratorio.appinombiliariaast.models;

public class Propietario {
    private int DNI;
    private String Nombre;
    private String Apellido;
    private String Telefono;
    private String Email;
    private String Direccion;
    private String Password;

    public Propietario(int DNI, String nombre, String apellido, String telefono, String email, String direccion, String password) {
        this.DNI = DNI;
        Nombre = nombre;
        Apellido = apellido;
        Telefono = telefono;
        Email = email;
        Direccion = direccion;
        Password = password;
    }

    public int getDNI() {
        return DNI;
    }

    public void setDNI(int DNI) {
        this.DNI = DNI;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
