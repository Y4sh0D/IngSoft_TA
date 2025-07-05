package com.example.ta_avance.viewmodel;

import android.content.Context;

import com.example.ta_avance.api.ApiClient;
import com.example.ta_avance.api.AuthApiService;
import com.example.ta_avance.dto.valoracion.ValoracionDto;
import com.example.ta_avance.dto.valoracion.ValoracionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListarValoracionViewModel {

    public interface ValoracionCallback{
        void onSuccess(List<ValoracionDto> valoraciones);
        void onError(String mensaje);
    }

    public void obtenerValoraciones(Context context, ValoracionCallback callback){
        AuthApiService api = ApiClient.getRetrofit(context,true).create(AuthApiService.class);
        Call<ValoracionResponse> call = api.listarValoraciones();

        call.enqueue(new Callback<ValoracionResponse>() {
            @Override
            public void onResponse(Call<ValoracionResponse> call, Response<ValoracionResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onError("Error al obtener usuarios");
                }
            }

            @Override
            public void onFailure(Call<ValoracionResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
