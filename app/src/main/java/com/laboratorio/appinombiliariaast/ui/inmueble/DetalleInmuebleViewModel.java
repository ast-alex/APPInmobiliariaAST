package com.laboratorio.appinombiliariaast.ui.inmueble;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.laboratorio.appinombiliariaast.models.InmuebleDetalleViewModel;
import com.laboratorio.appinombiliariaast.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<InmuebleDetalleViewModel> inmuebleDetalle;

    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<InmuebleDetalleViewModel> getDetalle() {
        if (inmuebleDetalle == null) {
            inmuebleDetalle = new MutableLiveData<>();
        }
        return inmuebleDetalle;
    }

    public void cargarDetalle(int iD_inmueble) {
        SharedPreferences sp = getApplication().getSharedPreferences("usuario", 0);
        String token = sp.getString("token", null);

        if (token != null) {
            ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
            Call<InmuebleDetalleViewModel> pcall = api.getDetalleInmueble(iD_inmueble, "Bearer " + token);

            pcall.enqueue(new Callback<InmuebleDetalleViewModel>() {
                @Override
                public void onResponse(Call<InmuebleDetalleViewModel> call, Response<InmuebleDetalleViewModel> response) {
                    if(response.isSuccessful() && response.body() != null) {
                        inmuebleDetalle.postValue(response.body());
                        Log.d("InmuebleViewModel", "Inmueble cargado: " + response.body().getDireccion());
                    }else {
                        Log.d("DetalleInmuebleViewModel", "Error en la respuesta: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<InmuebleDetalleViewModel> call, Throwable throwable) {
                    Log.d("DetalleInmuebleViewModel", "Error en la peticion: " + throwable.getMessage());
                    Toast.makeText(getApplication(), " Error en el server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void actualizarDisponibilidad(int inmuebleId, boolean disponible) {
        SharedPreferences sp = getApplication().getSharedPreferences("usuario", 0);
        String token = sp.getString("token", null);

        if (token != null) {
            ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
            Call<Void> pcall = api.actualizarDisponibilidad(inmuebleId, disponible, "Bearer " + token);

            pcall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()) {
                        InmuebleDetalleViewModel idvm = inmuebleDetalle.getValue();
                        if(idvm != null) {
                            idvm.setDisponibilidad(disponible);
                            inmuebleDetalle.postValue(idvm);
                        }
                        Toast.makeText(getApplication(), "Disponibilidad actualizada", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplication(), "Error al cambiar la disponibilidad", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable throwable) {
                    Log.d("InmuebleViewModel", "Error en la peticion: " + throwable.getMessage());
                    Toast.makeText(getApplication(), " Error en el server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}