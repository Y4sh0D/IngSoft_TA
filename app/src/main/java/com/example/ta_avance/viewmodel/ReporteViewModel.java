package com.example.ta_avance.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.ta_avance.api.ApiClient;
import com.example.ta_avance.api.AuthApiService;
import com.example.ta_avance.dto.reporte.DtoReporte;
import com.example.ta_avance.dto.reporte.DtoReporteResponse;

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReporteViewModel extends ViewModel {

    public interface ReporteCallback {
        void onSuccess(DtoReporte reporte);
        void onError(String mensaje);
    }
    public void obtenerReporte(Context context, LocalDate fechaInicio, LocalDate fechaFin, String servicio, ReporteCallback callback){
        AuthApiService api = ApiClient.getRetrofit(context,true).create(AuthApiService.class);
        Call<DtoReporteResponse> call = api.obtenerReporte(fechaInicio, fechaFin, servicio);

        call.enqueue(new Callback<DtoReporteResponse>() {
            @Override
            public void onResponse(Call<DtoReporteResponse> call, Response<DtoReporteResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onError("Error al obtener reporte");
                }
            }

            @Override
            public void onFailure(Call<DtoReporteResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
