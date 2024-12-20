package com.laboratorio.appinombiliariaast.ui.forgotpassword;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.laboratorio.appinombiliariaast.models.RestablecerContrasenaRequest;
import com.laboratorio.appinombiliariaast.request.ApiClient;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordViewModel extends AndroidViewModel {

    public ChangePasswordViewModel(@NonNull Application application) {
        super(application);
    }

    public void cambiarPassword(int idPropietario, String token, String newPassword, final Runnable onSuccess) {
        RestablecerContrasenaRequest request = new RestablecerContrasenaRequest(token, newPassword);


        ApiClient.getInmobiliariaService().cambiarPassword(idPropietario, request).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Contraseña cambiada con exito", Toast.LENGTH_SHORT).show();
                    if (onSuccess != null) {
                        onSuccess.run();
                    }
                }else{
                    Toast.makeText(getApplication(), "Error al cambiar la contraseña", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(getApplication(), " Error en el server" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}