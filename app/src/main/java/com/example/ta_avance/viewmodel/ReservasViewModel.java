package com.example.ta_avance.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ta_avance.api.ApiClient;
import com.example.ta_avance.api.AuthApiService;
import com.example.ta_avance.dto.login.LoginRequest;
import com.example.ta_avance.dto.login.LoginResponseSimple;
import com.example.ta_avance.dto.reserva.ReservaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservasViewModel extends ViewModel {

    private final MutableLiveData<List<ReservaResponse>> reservasLiveData = new MutableLiveData<>();
    public MutableLiveData<String> mensajeError = new MutableLiveData<>();
    public MutableLiveData<Boolean> cambioEstadoExitoso = new MutableLiveData<>();
    public final MutableLiveData<LoginRequest> usuarioPorIdLiveData = new MutableLiveData<>();

    public LiveData<List<ReservaResponse>> getReservas() {
        return reservasLiveData;
    }

    public void cargarReservas(Context context, String fecha, String estado) {
        AuthApiService api = ApiClient.getRetrofit(context, true).create(AuthApiService.class);
        Call<List<ReservaResponse>> call = api.listarReservas(fecha, estado);

        call.enqueue(new Callback<List<ReservaResponse>>() {
            @Override
            public void onResponse(Call<List<ReservaResponse>> call, Response<List<ReservaResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    reservasLiveData.postValue(response.body());
                }else {
                    mensajeError.postValue("Error al mostrar las reservas: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ReservaResponse>> call, Throwable t) {
                mensajeError.postValue("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void cambiarEstadoReserva(Context context, Long reservaId, String nuevoEstado, String motivoDescripcion) {
        AuthApiService api = ApiClient.getRetrofit(context, true).create(AuthApiService.class);
        Call<Void> call = api.cambiarEstadoReserva(reservaId, nuevoEstado, motivoDescripcion);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    cambioEstadoExitoso.postValue(true);
                } else {
                    mensajeError.postValue("Error al cambiar el estado: " + response.code());
                    cambioEstadoExitoso.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mensajeError.postValue("Error de conexión: " + t.getMessage());
                cambioEstadoExitoso.postValue(false);
            }
        });
    }

    public void obtenerUsuarioPorId(Context context, long usuarioId) {
        AuthApiService api = ApiClient.getRetrofit(context, true).create(AuthApiService.class);
        Call<LoginResponseSimple> call = api.obtenerUsuarioPorId(usuarioId);

        call.enqueue(new Callback<LoginResponseSimple>() {
            @Override
            public void onResponse(Call<LoginResponseSimple> call, Response<LoginResponseSimple> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    usuarioPorIdLiveData.postValue(response.body().getData());
                } else {
                    mensajeError.postValue("Error al obtener datos del usuario: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<LoginResponseSimple> call, Throwable t) {
                mensajeError.postValue("Error de conexión: " + t.getMessage());
            }
        });
    }

}

