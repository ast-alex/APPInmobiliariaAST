package com.laboratorio.appinombiliariaast.models;

public class ResetPasswordViewModel {
    private String nuevaPassword;
    private String confirmarPassword;

    public ResetPasswordViewModel(String nuevaPassword, String confirmarPassword) {
        this.nuevaPassword = nuevaPassword;
        this.confirmarPassword = confirmarPassword;
    }

    public String getNuevaPassword() {
        return nuevaPassword;
    }

    public void setNuevaPassword(String nuevaPassword) {
        this.nuevaPassword = nuevaPassword;
    }

    public String getConfirmarPassword() {
        return confirmarPassword;
    }

    public void setConfirmarPassword(String confirmarPassword) {
        this.confirmarPassword = confirmarPassword;
    }
}
