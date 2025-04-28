package com.example.ta_avance.api;

import com.example.ta_avance.modelo.LoginRequest;
import com.example.ta_avance.modelo.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
