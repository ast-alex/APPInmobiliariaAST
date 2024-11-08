package com.laboratorio.appinombiliariaast.ui.contrato;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.laboratorio.appinombiliariaast.models.Contrato;
import com.laboratorio.appinombiliariaast.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {
    private MutableLiveData<Contrato> contratoDetalle;

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Contrato> getContratoDetalle() {
        if (contratoDetalle == null) {
            contratoDetalle = new MutableLiveData<>();

        }
        return contratoDetalle;
    }

    public void cargarDetalle(int iD_contrato){
        SharedPreferences sp = getApplication().getSharedPreferences("usuario", 0);
        String token = sp.getString("token", null);

        if (token != null)
        {
            ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
            Call<Contrato> pcall = api.getContrato(iD_contrato, "Bearer " + token);

            pcall.enqueue(new Callback<Contrato>() {
                @Override
                public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        contratoDetalle.postValue(response.body());
                        Log.d("ContratoViewModel", "Contrato cargado: " + response.body().getFecha_Inicio());
                    }else{
                        Log.d("ContratoViewModel", "Error en la respuesta: " + response.code());
                        Log.d("ContratoViewModel", "Cuerpo de la respuesta: " + response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<Contrato> call, Throwable throwable) {
                    Log.d("ContratoViewModel", "Error en la llamada: " + throwable.getMessage());
                    Toast.makeText(getApplication(), "Error en el server", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }



}