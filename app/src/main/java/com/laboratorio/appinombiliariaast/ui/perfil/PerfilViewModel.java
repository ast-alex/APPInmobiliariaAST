package com.laboratorio.appinombiliariaast.ui.perfil;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
    private boolean avatarUpdated = false;

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
            avatarUpdated = false;
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

                        //guardar datos del propietario en shared preferences
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("user_name", propietario.getNombre() + " " + propietario.getApellido());
                        editor.putString("user_email", propietario.getEmail());
                        editor.putString("user_avatar_url", propietario.getAvatar());
                        editor.apply();
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

            // Convertir cada campo a RequestBody
            RequestBody dniBody = RequestBody.create(MediaType.parse("text/plain"), dni);
            RequestBody nombreBody = RequestBody.create(MediaType.parse("text/plain"), nombre);
            RequestBody apellidoBody = RequestBody.create(MediaType.parse("text/plain"), apellido);
            RequestBody telefonoBody = RequestBody.create(MediaType.parse("text/plain"), telefono);
            RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), email);
            RequestBody direccionBody = RequestBody.create(MediaType.parse("text/plain"), direccion);

            // Preparar la parte del avatar
            MultipartBody.Part avatarPart = null;
            if (avatarUpdated && avatarUri != null) {
                try {
                    ContentResolver resolver = getApplication().getContentResolver();
                    InputStream inputStream = resolver.openInputStream(avatarUri);

                    // Crear archivo temporal
                    File tempFile = File.createTempFile("avatar", ".jpg", getApplication().getCacheDir());
                    OutputStream outputStream = new FileOutputStream(tempFile);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }

                    outputStream.close();
                    inputStream.close();

                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), tempFile);
                    avatarPart = MultipartBody.Part.createFormData("avatarFile", getFileNameFromUri(avatarUri), fileBody);

                } catch (IOException e) {
                    Log.e("PerfilViewModel", "Error al procesar la imagen: " + e.getMessage());
                }
            }

            // Llamada a la API con los datos individuales como Multipart
            Call<Propietario> pcall = api.modificarPropietario(
                    "Bearer " + token,
                    dniBody,
                    nombreBody,
                    apellidoBody,
                    telefonoBody,
                    emailBody,
                    direccionBody,
                    avatarPart
            );

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

    @SuppressLint("Range")
    private String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getApplication().getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public void recibirFoto(ActivityResult result){
        if(result.getResultCode() == RESULT_OK && result.getData() != null){
            uriFoto.setValue(result.getData().getData());
            avatarUpdated = true;
        }
    }
}