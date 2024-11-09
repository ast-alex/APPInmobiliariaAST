package com.laboratorio.appinombiliariaast.ui.forgotpassword;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.common.api.Api;
import com.laboratorio.appinombiliariaast.MainActivity;
import com.laboratorio.appinombiliariaast.models.ResetPasswordViewModel;
import com.laboratorio.appinombiliariaast.request.ApiClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordViewModel extends AndroidViewModel {

    public ForgotPasswordViewModel(@NonNull Application application) {
        super(application);
    }


    public void solicitarRecuperacion(String email){
        if(email.isEmpty()){
            Toast.makeText(getApplication(), "Ingrese un correo elctronico", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
        Call<ResponseBody> pcall = api.solicitarRecuperacion(email);

        pcall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getApplication(), "Correo enviado", Toast.LENGTH_SHORT).show();
                }
               else{
                    Toast.makeText(getApplication(), "Error al enviar el correo", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(getApplication(), " Error en el server" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }




}
