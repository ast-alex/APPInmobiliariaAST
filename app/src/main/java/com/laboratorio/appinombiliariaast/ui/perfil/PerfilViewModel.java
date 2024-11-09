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

import com.google.gson.Gson;
import com.laboratorio.appinombiliariaast.models.Propietario;
import com.laboratorio.appinombiliariaast.request.ApiClient;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class PerfilViewModel extends AndroidViewModel {
    private MutableLiveData<Propietario> mPropietario;
    private MutableLiveData<Boolean> isEdit = new MutableLiveData<>(false);
    private MutableLiveData<Uri> uriFoto;

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


    public MutableLiveData<Uri> getUriFoto() {
        if (uriFoto == null) {
            uriFoto = new MutableLiveData<>();
        }
        return uriFoto;
    }


    public void toogleEditMode(String dni, String nombre, String apellido, String telefono, String email, String direccion) {
        if (isEdit.getValue() != null && isEdit.getValue()) {
            //modo edicion; se guardan los cambios
            Uri avatarUri = getUriFoto().getValue();
            actualizarPropietario(dni, nombre, apellido, telefono, email, direccion, avatarUri);
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

    public void actualizarPropietario(String dni, String nombre, String apellido, String telefono, String email, String direccion, Uri avatarUri) {
        SharedPreferences sp = getApplication().getSharedPreferences("usuario", 0);
        String token = sp.getString("token", null);

        if (token != null) {
            ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();

            // Convertir los datos del propietario a Multipart
            RequestBody dniPart = RequestBody.create(MediaType.parse("text/plain"), dni);
            RequestBody nombrePart = RequestBody.create(MediaType.parse("text/plain"), nombre);
            RequestBody apellidoPart = RequestBody.create(MediaType.parse("text/plain"), apellido);
            RequestBody telefonoPart = RequestBody.create(MediaType.parse("text/plain"), telefono);
            RequestBody emailPart = RequestBody.create(MediaType.parse("text/plain"), email);
            RequestBody direccionPart = RequestBody.create(MediaType.parse("text/plain"), direccion);

            // Convertir el archivo de imagen a Multipart
            File avatarFile = new File(avatarUri.getPath());
            RequestBody avatarBody = RequestBody.create(MediaType.parse("image/*"), avatarFile);
            MultipartBody.Part avatarPart = MultipartBody.Part.createFormData("avatar", avatarFile.getName(), avatarBody);

            // Llamada a la API con todos los datos como Multipart
            Call<Propietario> pcall = api.modificarPropietario("Bearer " + token, dniPart, nombrePart, apellidoPart, telefonoPart, emailPart, direccionPart, avatarPart);
            pcall.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Propietario pActualizado = response.body();
                        Log.d("PerfilViewModel", "Propietario actualizado: " + pActualizado.getNombre());
                        mPropietario.setValue(pActualizado);
                        obtenerPropietario();
                    } else {
                        Log.d("PerfilViewModel", "Error al actualizar propietario: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable throwable) {
                    Log.d("PerfilViewModel", "Error en la peticion: " + throwable.getMessage());
                    Toast.makeText(getApplication(), "Error en el server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void recibirFoto(ActivityResult result){
        if(result.getResultCode() == RESULT_OK && result.getData() != null){
            uriFoto.setValue(result.getData().getData());
        }
    }
}