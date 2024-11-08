package com.laboratorio.appinombiliariaast.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laboratorio.appinombiliariaast.models.Contrato;
import com.laboratorio.appinombiliariaast.models.Inmueble;
import com.laboratorio.appinombiliariaast.models.InmuebleDetalleViewModel;
import com.laboratorio.appinombiliariaast.models.Login;
import com.laboratorio.appinombiliariaast.models.Pago;
import com.laboratorio.appinombiliariaast.models.Pass;
import com.laboratorio.appinombiliariaast.models.Propietario;
import com.laboratorio.appinombiliariaast.models.ResetPasswordViewModel;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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


        @PUT("Propietario/modificar")
        Call<Propietario> modificarPropietario(@Header("Authorization") String token, @Body Propietario propietario);

        @PUT("Propietario/cambiar-password")
        Call<String> cambiarPassword(@Header("Authorization") String token, @Body Pass pass);

        @POST("Propietario/forgot-password")
        Call<String> forgotPassword(@Body String email);

        @POST("Propietario/reset-password")
        Call<String> resetPassword(
                @Query("email") String email,
                @Query("token") String token,
                @Body ResetPasswordViewModel model
        );

        @GET("Inmueble/inmueblespropietario")
        Call<List<Inmueble>> getInmuebles(@Header("Authorization") String token);

        @GET("Inmueble/detalle/{id}")
        Call<InmuebleDetalleViewModel> getDetalleInmueble(@Path("id") int id, @Header("Authorization") String token);

        @Headers({"Content-Type: application/json"})
        @PATCH("Inmueble/disponibilidad/{id}")
        Call<Void> actualizarDisponibilidad(@Path("id") int id, @Body boolean disponibilidad, @Header("Authorization") String token);

        @Multipart
        @POST("Inmueble/crearInmueble")
        Call<Inmueble> crearInmueble(
                @Part("direccion") String direccion,
                @Part("uso") int uso,
                @Part("tipo") int tipo,
                @Part("cantidad_Ambientes") int cantidad_Ambientes,
                @Part("latitud") double latitud,
                @Part("longitud") double longitud,
                @Part("precio") double precio,
                @Part MultipartBody.Part foto,
                @Header("Authorization") String token
        );

        @GET("Contrato/inmuebles-alquilados")
        Call<List<Contrato>> getContratos(@Header("Authorization") String token);

        @GET("Contrato/detalle/{idContrato}")
        Call<Contrato> getContrato(@Path("idContrato") int idContrato, @Header("Authorization") String token);

        @GET("Pago/pagos-contrato/{idContrato}")
        Call<List<Pago>> getPagos(@Path("idContrato") int idContrato, @Header("Authorization") String token);
    }




}
