package com.laboratorio.appinombiliariaast.ui.perfil;

import android.app.Application;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.laboratorio.appinombiliariaast.models.Pass;
import com.laboratorio.appinombiliariaast.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CambiarPassViewModel extends AndroidViewModel {
    public CambiarPassViewModel(@NonNull Application application) {
        super(application);
    }

    public void cambiarPassword(String PasswordAcutal, String NuevaPassword, String ConfirmarPassword) {
        //validar campos vacios
        if(PasswordAcutal.isEmpty() || NuevaPassword.isEmpty() || ConfirmarPassword.isEmpty()){
            Toast.makeText(getApplication(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        //validar password
        if(!NuevaPassword.equals(ConfirmarPassword)){
            Toast.makeText(getApplication(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        //logica para realizar peticion al endpoint utilizando retrofit y shared preferences
        SharedPreferences sp = getApplication().getSharedPreferences("usuario", 0);
        String token = sp.getString("token", null);

        if (token != null) {
            ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
            Pass pass = new Pass(PasswordAcutal, NuevaPassword, ConfirmarPassword);
            Call<String> pcall = api.cambiarPassword("Bearer " + token, pass);

            pcall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplication(),  "Contraseña cambiada con exito" + response.body(), Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(getApplication(), "Error al cambiar la contraseña" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    Toast.makeText(getApplication(), " Error en el server" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}