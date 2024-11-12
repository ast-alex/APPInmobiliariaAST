package com.laboratorio.appinombiliariaast.ui.inmueble;

import static android.app.Activity.RESULT_OK;
import static android.app.PendingIntent.getActivity;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.laboratorio.appinombiliariaast.models.Inmueble;
import com.laboratorio.appinombiliariaast.request.ApiClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Uri> uriFoto;
    private MutableLiveData<String> msj;
    private Context context;

    public CrearInmuebleViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public MutableLiveData<Uri> getUriFoto() {
        if (uriFoto == null) {
            uriFoto = new MutableLiveData<>();
        }
        return uriFoto;
    }

    public MutableLiveData<String> getMsj() {
        if (msj == null) {
            msj = new MutableLiveData<>();
        }
        return msj;

    }

    public MultipartBody.Part convertUriToFile(Uri uri, Context context) {
        try {
            InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
            File file = new File(getActivity().getCacheDir(), "image.png");
            OutputStream outputStream = new FileOutputStream(file);

            // Copiar los datos de la URI al archivo
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();

            // Crear un MultipartBody.Part para enviar la imagen
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            return MultipartBody.Part.createFormData("fotoFile", file.getName(), requestBody);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Context getActivity() {
        return getApplication().getApplicationContext();
    }

    public void recibirFoto(ActivityResult result){
        if(result.getResultCode() == RESULT_OK && result.getData() != null){
            uriFoto.setValue(result.getData().getData());
        }
    }

    public void crearInmueble(String direccion, String uso, String tipo, String cantidad_Ambientes, String latitud, String longitud, String precio){
        // Validación de campos vacíos
        if (direccion.isEmpty() || uso.isEmpty() || tipo.isEmpty() || cantidad_Ambientes.isEmpty() ||
                latitud.isEmpty() || longitud.isEmpty() || precio.isEmpty()) {
            msj.setValue("Todos los campos son obligatorios.");
            return;
        }

        //validacion foto
        Uri uri = uriFoto.getValue();
        if (uri == null) {
            msj.setValue("La foto es obligatoria.");
            return;
        }

        try {
            // Si los campos no están vacíos, se puede proceder a convertir los valores
            int usoInt = Integer.parseInt(uso);
            int tipoInt = Integer.parseInt(tipo);
            int cantidad_AmbientesInt = Integer.parseInt(cantidad_Ambientes);
            double latitudDouble = Double.parseDouble(latitud);
            double longitudDouble = Double.parseDouble(longitud);
            double precioDouble = Double.parseDouble(precio);

            // Preparar la imagen si está disponible
            MultipartBody.Part fotoPart = convertUriToFile(uri, getActivity());

            // Obtener token del usuario
            SharedPreferences sp = getApplication().getSharedPreferences("usuario", 0);
            String token = sp.getString("token", null);

            if (token != null) {
                // Llamar a la API para crear el inmueble
                ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
                Call<Inmueble> pcall = api.crearInmueble(direccion, usoInt, tipoInt, cantidad_AmbientesInt, latitudDouble, longitudDouble, precioDouble, fotoPart, "Bearer " + token);

                pcall.enqueue(new Callback<Inmueble>() {
                    @Override
                    public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Inmueble inmueble = response.body();
                            msj.setValue("Inmueble creado con éxito");
                        } else {
                            msj.setValue("Error al crear el inmueble: " + response.errorBody().toString());
                            Log.d("CrearInmuebleViewModel", "Error en la respuesta: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Inmueble> call, Throwable throwable) {
                        msj.setValue("Error en el servidor: " + throwable.getMessage());
                        Log.d("CrearInmuebleViewModel", "Error en la petición: " + throwable.getMessage());
                    }
                });
            } else {
                msj.setValue("Token no disponible. Por favor, inicie sesión.");
            }
        } catch (NumberFormatException e) {
            msj.setValue("Por favor, ingrese valores válidos para los campos numéricos.");
        }
    }

}