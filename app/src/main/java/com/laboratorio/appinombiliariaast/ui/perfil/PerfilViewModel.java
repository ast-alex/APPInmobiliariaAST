package com.laboratorio.appinombiliariaast.ui.perfil;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
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
    private MutableLiveData<Propietario> mPropietario;
    private MutableLiveData<Boolean> isEdit = new MutableLiveData<>(false);

    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Propietario> getMPropietario() {
        if (mPropietario == null) {
            mPropietario = new MutableLiveData<>();
        }
        return mPropietario;
    }

    public LiveData<Boolean> getIsEdit() {
        return isEdit;
    }


    public void toogleEditMode(String dni, String nombre, String apellido, String telefono, String email, String direccion) {
        if (isEdit.getValue() != null && isEdit.getValue()) {
            //modo edicion; se guardan los cambios
            actualizarPropietario(dni, nombre, apellido, telefono, email, direccion);
        }
        isEdit.setValue(isEdit.getValue() == null || !isEdit.getValue());
    }

    public void obtenerPropietario() {
        //token del shared preferences
        SharedPreferences sp = getApplication().getSharedPreferences("usuario", 0);
        String token = sp.getString("token", null);

        if (token != null) {
            ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
            Call<Propietario> pcall = api.getMPropietario("Bearer " + token);


            pcall.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    
                    if (response.isSuccessful() && response.body() != null) {
                        Propietario propietario = response.body();
                        Log.d("PerfilViewModel", "Propietario cargado: " + response.body().getNombre());

                        mPropietario.setValue(propietario);
                    }else{
                        Log.d("PerfilViewModel", "Error al obtener propietario: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable throwable) {
                    Toast.makeText(getApplication(), " Error en el server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void actualizarPropietario ( String dni,String nombre,String apellido,  String telefono,String email, String direccion)
    {
        SharedPreferences sp = getApplication().getSharedPreferences("usuario", 0);
        String token = sp.getString("token", null);

        if (token != null)
        {
            ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();

            Propietario propietarioModificado = new Propietario();
            propietarioModificado.setDni(dni);
            propietarioModificado.setNombre(nombre);
            propietarioModificado.setApellido(apellido);
            propietarioModificado.setTelefono(telefono);
            propietarioModificado.setEmail(email);
            propietarioModificado.setDireccion(direccion);

            Call<Propietario> pcall = api.modificarPropietario("Bearer " + token, propietarioModificado);
            pcall.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Propietario pActualizado = response.body();
                        Log.d("PerfilViewModel", "Propietario actualizado: " + response.body().getNombre());
                        mPropietario.setValue(pActualizado);

                        obtenerPropietario();
                    }else{
                        Log.d("PerfilViewModel", "Error al actualizar propietario: " + response.code());
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