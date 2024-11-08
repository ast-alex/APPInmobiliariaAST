package com.laboratorio.appinombiliariaast.ui.pago;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.laboratorio.appinombiliariaast.models.Contrato;
import com.laboratorio.appinombiliariaast.models.Pago;
import com.laboratorio.appinombiliariaast.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagoViewModel extends AndroidViewModel {
    private MutableLiveData<List<Pago>> pagosDetalle;

    public PagoViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Pago>> getPagos() {
        if (pagosDetalle == null) {
            pagosDetalle = new MutableLiveData<>();
        }
        return pagosDetalle;
    }

    public void cargarPagos(int idContrato)
    {
        SharedPreferences sp = getApplication().getSharedPreferences("usuario", 0);
        String token = sp.getString("token", null);

        if (token != null) {
            ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
            Call<List<Pago>> pcall = api.getPagos(idContrato, "Bearer " + token);

            pcall.enqueue(new Callback<List<Pago>>() {
                @Override
                public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        {
                            pagosDetalle.postValue(response.body());
                        }
                        Log.d("ContratoViewModel", "Contrato cargado: " + response.body().get(0).getFecha_pago());
                    }else{
                        Log.d("ContratoViewModel", "Error en la respuesta: " + response.code());
                        Log.d("ContratoViewModel", "Cuerpo de la respuesta: " + response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<List<Pago>> call, Throwable throwable) {
                    Toast.makeText(getApplication(), "Error en el servidor", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}