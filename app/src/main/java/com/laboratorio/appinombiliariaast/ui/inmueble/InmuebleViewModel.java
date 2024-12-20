package com.laboratorio.appinombiliariaast.ui.inmueble;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.laboratorio.appinombiliariaast.models.Inmueble;

import com.laboratorio.appinombiliariaast.request.ApiClient;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<List<Inmueble>> inmuebles;

    public InmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Inmueble>> getInmuebles() {
        if (inmuebles == null) {
            inmuebles = new MutableLiveData<>();

        }
        return inmuebles;
    }

    public void cargarInmuebles() {
        SharedPreferences sp = getApplication().getSharedPreferences("usuario", 0);
        String token = sp.getString("token", null);
        Log.d("InmuebleViewModel", "Token obtenido: " + token);

        if (token != null) {
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
        Call<List<Inmueble>> pcall = api.getInmuebles("Bearer " + token);

            pcall.enqueue(new Callback<List<Inmueble>>() {
                @Override
                public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        inmuebles.postValue(response.body());
                        Log.d("InmuebleViewModel", "Inmuebles cargados: " + response.body().size());
                    } else {
                        Log.d("InmuebleViewModel", "Error en la respuesta: " + response.code());
                        Log.d("InmuebleViewModel", "Cuerpo de la respuesta: " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                    Log.e("InmuebleViewModel", "Error en la carga de inmuebles: " + t.getMessage());
                    Toast.makeText(getApplication(), "Error en el servidor", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}