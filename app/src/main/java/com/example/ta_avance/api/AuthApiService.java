package com.example.ta_avance.api;

import com.example.ta_avance.modelo.LoginRequest;
import com.example.ta_avance.modelo.LoginResponse;
import com.example.ta_avance.modelo.ServiciosRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("auth/register")
    Call<Void> register(@Body LoginRequest registerRequest);

    @POST("servicio/crear")
    Call<Void> crear(@Body ServiciosRequest serviciosRequest);
}
