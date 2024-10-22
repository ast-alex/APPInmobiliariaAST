package com.laboratorio.appinombiliariaast.models;

public class Pass {
    private String PasswordActual;
    private String NuevaPassword;
    private String ConfirmarPassword;

    public Pass(String PasswordActual, String NuevaPassword, String ConfirmarPassword) {
        this.PasswordActual = PasswordActual;
        this.NuevaPassword = NuevaPassword;
        this.ConfirmarPassword = ConfirmarPassword;
    }

    public String getPasswordActual() {
        return PasswordActual;
    }

    public void setPasswordActual(String passwordActual) {
        PasswordActual = passwordActual;
    }

    public String getNuevaPassword() {
        return NuevaPassword;
    }

    public void setNuevaPassword(String nuevaPassword) {
        NuevaPassword = nuevaPassword;
    }

    public String getConfirmarPassword() {
        return ConfirmarPassword;
    }

    public void setConfirmarPassword(String confirmarPassword) {
        ConfirmarPassword = confirmarPassword;
    }
}
