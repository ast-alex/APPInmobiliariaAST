package com.laboratorio.appinombiliariaast.ui.forgotpassword;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.laboratorio.appinombiliariaast.MainActivity;
import com.laboratorio.appinombiliariaast.models.ResetPasswordViewModel;
import com.laboratorio.appinombiliariaast.request.ApiClient;

import retrofit2.Call;
import retrofit2.Response;

public class ForgotPasswordViewModel extends AndroidViewModel {
    private String email;
    private String token;

    public ForgotPasswordViewModel(@NonNull Application application) {
        super(application);
    }

    public void setEmailToken(String email, String token){
        this.email = email;
        this.token = token;
    }


    public void resetPassword(String nuevaPassword, String confirmarPassword) {
        ResetPasswordViewModel model = new ResetPasswordViewModel(nuevaPassword, confirmarPassword);

        ApiClient.getInmobiliariaService().resetPassword(email, token, model).enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplication(), "Contraseña cambiada", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    getApplication().startActivity(intent);
                }else{
                    Toast.makeText(getApplication(), "Error al cambiar la contraseña", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplication(), "Error en el server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
