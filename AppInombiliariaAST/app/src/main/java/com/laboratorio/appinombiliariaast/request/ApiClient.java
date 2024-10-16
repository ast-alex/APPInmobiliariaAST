package com.laboratorio.appinombiliariaast.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laboratorio.appinombiliariaast.models.Login;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class ApiClient {

    public static final String BASE_URL = "http://192.168.1.2:5166/api/";

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

    }
}
