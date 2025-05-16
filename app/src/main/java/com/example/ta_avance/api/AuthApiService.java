package com.example.ta_avance.api;

import com.example.ta_avance.dto.BarberoRequest;
import com.example.ta_avance.dto.BarberoResponse;
import com.example.ta_avance.dto.BarberoSimpleResponse;
import com.example.ta_avance.dto.LoginRequest;
import com.example.ta_avance.dto.LoginResponse;
import com.example.ta_avance.dto.RecuperacionRequest;
import com.example.ta_avance.dto.RecuperacionResponse;
import com.example.ta_avance.dto.RefreshRequest;
import com.example.ta_avance.dto.ServiciosRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AuthApiService {
    @POST("api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("api/auth/register")
    Call<Void> register(@Body LoginRequest registerRequest);

    @POST("api/servicio/crear")
    Call<Void> crear(@Body ServiciosRequest serviciosRequest);

    @POST("api/auth/refreshToken")
    Call<LoginResponse> refresh(@Body RefreshRequest refreshRequest);

    @POST("emailPassword/sendEmail")
    Call<RecuperacionResponse> recuperarContraseña(@Body RecuperacionRequest recuperacionRequest);

    @GET("api/barbero/listar")
    Call<BarberoResponse> listarBarberos();

    @POST("api/barbero/crear")
    Call<BarberoResponse> crearBarbero(@Body BarberoRequest request);

    @DELETE("api/barbero/eliminar/{id}")
    Call<BarberoResponse> eliminarBarbero(@Path("id") int id);

    @PUT("api/barbero/actualizar/{id}")
    Call<BarberoSimpleResponse> actualizarBarbero(@Path("id") int id, @Body BarberoRequest barberoRequest);

}
