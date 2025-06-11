package com.example.ta_avance.viewmodel;

import android.content.Context;

import com.example.ta_avance.api.ApiClient;
import com.example.ta_avance.api.AuthApiService;
import com.example.ta_avance.dto.login.LoginRequest;
import com.example.ta_avance.dto.login.LoginResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListarUsuarioViewModel {

    public interface UsuarioCallback {
        void onSuccess(List<LoginRequest> usuarios);
        void onError(String mensaje);
    }

    public void obtenerUsuarios(Context context, UsuarioCallback callback){
        AuthApiService api = ApiClient.getRetrofit(context, true).create(AuthApiService.class);
        Call<LoginResponse> call = api.listarUsuarios();

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onError("Error al obtener usuarios");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
