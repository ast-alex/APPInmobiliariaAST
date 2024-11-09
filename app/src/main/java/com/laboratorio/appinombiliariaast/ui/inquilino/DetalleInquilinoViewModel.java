package com.laboratorio.appinombiliariaast.ui.inquilino;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.laboratorio.appinombiliariaast.models.Inquilino;
import com.laboratorio.appinombiliariaast.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInquilinoViewModel extends AndroidViewModel {
    private MutableLiveData<Inquilino> inquilinoDetalle;


    public DetalleInquilinoViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Inquilino> getInquilinoDetalle() {
        if (inquilinoDetalle == null) {
            inquilinoDetalle = new MutableLiveData<>();
        }
        return inquilinoDetalle;
    }

    public void cargarDetalle(int iD_inquilino){
        SharedPreferences sp = getApplication().getSharedPreferences("usuario", 0);
        String token = sp.getString("token", null);

        if(token != null) {
            ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
            Call<Inquilino> pcall = api.getInquilino(iD_inquilino, "Bearer " + token);

            pcall.enqueue(new Callback<Inquilino>() {
                @Override
                public void onResponse(Call<Inquilino> call, Response<Inquilino> response) {
                    if(response.isSuccessful() && response.body() != null) {
                        inquilinoDetalle.postValue(response.body());
                        Log.d("InquilinoViewModel", "Inquilino cargado: " + response.body().getNombre());
                    }else {
                        Log.d("DetalleInquilinoViewModel", "Error en la respuesta: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Inquilino> call, Throwable throwable) {
                    Toast.makeText(getApplication(), " Error en el server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}