package com.laboratorio.appinombiliariaast.ui.contrato;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.laboratorio.appinombiliariaast.models.Contrato;
import com.laboratorio.appinombiliariaast.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoViewModel extends AndroidViewModel {
    private MutableLiveData<List<Contrato>> contratos;

    public ContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Contrato>> getContratos() {
        if (contratos == null) {
            contratos = new MutableLiveData<>();
            cargarContratos();
        }
        return contratos;
    }

    private void cargarContratos() {
        SharedPreferences sp = getApplication().getSharedPreferences("usuario", 0);
        String token = sp.getString("token", null);
        Log.d("ContratoViewModel", "Token obtenido: " + token);

        if(token != null) {
            ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
            Call<List<Contrato>> pcall = api.getContratos("Bearer " + token);

            pcall.enqueue(new Callback<List<Contrato>>() {
                @Override
                public void onResponse(Call<List<Contrato>> call, Response<List<Contrato>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        contratos.postValue(response.body());
                        Log.d("ContratoViewModel", "Contratos cargados: " + response.body().size());
                    } else {
                        Log.d("ContratoViewModel", "Error en la respuesta: " + response.code());
                        Log.d("ContratoViewModel", "Cuerpo de la respuesta: " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<List<Contrato>> call, Throwable throwable) {
                    Log.e("ContratoViewModel", "Error en la carga de contratos: " + throwable.getMessage());
                    Toast.makeText(getApplication(), "Error en el servidor", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}