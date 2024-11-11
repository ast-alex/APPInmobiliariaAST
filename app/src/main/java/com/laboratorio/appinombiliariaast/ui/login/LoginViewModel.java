package com.laboratorio.appinombiliariaast.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.laboratorio.appinombiliariaast.MainActivity;
import com.laboratorio.appinombiliariaast.models.Login;
import com.laboratorio.appinombiliariaast.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private Context context;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public void callLogin(String email, String password){
        //instancia login
        Login login = new Login(email, password);
        ApiClient.InmobiliariaService api= ApiClient.getInmobiliariaService();
        Call<String> call = api.callLogin(login);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && response.body() != null){
                    String token = response.body();
                    //guardar token con shared preferences APICLIENT
                    ApiClient.Guardar(getApplication().getApplicationContext(), token);
                    Log.d("bearer token: ", token);

                    //redirigir a MainActivity
                    Intent intent = new Intent(getApplication().getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getApplication().getApplicationContext().startActivity(intent);
                }else{
                    Toast.makeText( getApplication().getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.e("Error", throwable.getMessage(), throwable);
                Toast.makeText( getApplication().getApplicationContext(), "Error en el server" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Método para realizar la llamada telefónica
    public void makePhoneCall(String phoneNumber) {
        // Verificar permisos antes de realizar la llamada
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent i2 = new Intent(Intent.ACTION_CALL);
            i2.setData(Uri.parse("tel:" + phoneNumber));
            i2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i2);
        } else {
            // Si no tiene permisos, se puede solicitar aquí si es necesario
            Toast.makeText(context, "Permiso para llamar no concedido", Toast.LENGTH_SHORT).show();
        }
    }


}
