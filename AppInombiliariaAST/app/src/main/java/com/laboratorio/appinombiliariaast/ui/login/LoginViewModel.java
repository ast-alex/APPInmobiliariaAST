package com.laboratorio.appinombiliariaast.ui.login;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.laboratorio.appinombiliariaast.models.Login;
import com.laboratorio.appinombiliariaast.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void callLogin(String email, String password){
        //instancia login
        Login login = new Login(email, password);
        ApiClient.InmobiliariaService api= ApiClient.getInmobiliariaService();
        Call<String> call = api.callLogin(login);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    Log.d("Salida", response.body());
                }else{
                    Toast.makeText( getApplication().getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.d("Error", throwable.getMessage());
            }
        });
    }


}
