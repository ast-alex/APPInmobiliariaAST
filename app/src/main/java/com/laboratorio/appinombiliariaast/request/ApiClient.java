package com.laboratorio.appinombiliariaast.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laboratorio.appinombiliariaast.models.Login;
import com.laboratorio.appinombiliariaast.models.Propietario;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public class ApiClient {

    private static SharedPreferences sp;

    private static SharedPreferences conectar(Context context){
       if (sp == null){
           sp = context.getSharedPreferences("usuario",0);
       }
        return sp;
    }
    public static final String BASE_URL = "http://192.168.1.2:5166/api/";


    public static void Guardar(Context context, String token){
        SharedPreferences sp = conectar(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String Leer (Context context){
        SharedPreferences sp = conectar(context);
        return sp.getString("token", null);
    }

    public static InmobiliariaService getInmobiliariaService(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(InmobiliariaService.class);
    };

    public interface InmobiliariaService{
        @POST("Propietario/login")
        Call<String> callLogin(@Body Login login);

        @GET("Propietario/perfil")
        Call<Propietario> getMPropietario(@Header("Authorization") String token);

    }




}
