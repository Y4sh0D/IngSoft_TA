package com.example.ta_avance.api;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://10.0.2.2:8080/"; // Cambiar por IP si es dispositivo real

    public static Retrofit getRetrofit(Context context, boolean withAuth) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)  // Espera para conectarse al servidor
                .readTimeout(30, TimeUnit.SECONDS)     // Espera para leer respuesta
                .writeTimeout(30, TimeUnit.SECONDS); // Espera para enviar datos

        if (withAuth) {
            clientBuilder.addInterceptor(new AuthInterceptor(context));
        }

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
