package com.example.ta_avance.api;

import com.example.ta_avance.dto.LoginRequest;
import com.example.ta_avance.dto.LoginResponse;
import com.example.ta_avance.dto.RecuperacionRequest;
import com.example.ta_avance.dto.RecuperacionResponse;
import com.example.ta_avance.dto.RefreshRequest;
import com.example.ta_avance.dto.ServiciosRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
}
