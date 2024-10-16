package com.laboratorio.appinombiliariaast.ui.perfil;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.laboratorio.appinombiliariaast.models.Propietario;
import com.laboratorio.appinombiliariaast.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {
    private  MutableLiveData<Propietario> mPropietario;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Propietario> getMPropietario() {
        if (mPropietario == null) {
            mPropietario = new MutableLiveData<>();
        }
        return mPropietario;
    }

    public void obtenerPropietario() {
        //token del shared preferences
        SharedPreferences sp = getApplication().getSharedPreferences("usuario", 0);
        String token = sp.getString("token", null);

        if (token != null) {
            ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
            Call<Propietario> call = api.getMPropietario("Bearer " + token);


            call.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful()) {
                        mPropietario.setValue(response.body());
                        Log.d("PerfilViewModel", response.body().toString());
                    }else{
                        Log.d("PerfilViewModel", response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable throwable) {
                    Toast.makeText(getApplication(), " Error en el server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}