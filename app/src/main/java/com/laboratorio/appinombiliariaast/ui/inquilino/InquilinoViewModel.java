package com.laboratorio.appinombiliariaast.ui.inquilino;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.laboratorio.appinombiliariaast.models.Inmueble;
import com.laboratorio.appinombiliariaast.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinoViewModel extends AndroidViewModel {
    private MutableLiveData<List<Inmueble>> inmuebles;

    public InquilinoViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<List<Inmueble>> getInmuebles() {
        if (inmuebles == null) {
            inmuebles = new MutableLiveData<>();
            getInmueblesAlquilados();
        }

        return inmuebles;
    }

    private void getInmueblesAlquilados() {
        SharedPreferences sp = getApplication().getSharedPreferences("usuario", 0);
        String token = sp.getString("token", null);

        if(token != null) {
            ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
            Call<List<Inmueble>> pcall = api.getInmueblesAlquilados("Bearer " + token);

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
                public void onFailure(Call<List<Inmueble>> call, Throwable throwable) {
                    Toast.makeText(getApplication(), "Error en el server", Toast.LENGTH_SHORT).show();
                }
            });

        }



    }
}