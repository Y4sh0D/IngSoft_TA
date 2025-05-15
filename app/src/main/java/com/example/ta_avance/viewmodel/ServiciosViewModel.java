package com.example.ta_avance.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.ta_avance.api.ApiClient;
import com.example.ta_avance.api.AuthApiService;
import com.example.ta_avance.dto.ServiciosRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiciosViewModel extends AndroidViewModel {

    private final AuthApiService authApiService;
    private final MutableLiveData<String> mensaje = new MutableLiveData<>();

    public ServiciosViewModel(@NonNull Application application) {
        super(application);
        authApiService = ApiClient.getRetrofit(application, true).create(AuthApiService.class);
    }

    public void registrarServicio(String nombre, String precioStr) {
        if (nombre.isEmpty() || precioStr.isEmpty()) {
            mensaje.setValue("Por favor completa todos los campos");
            return;
        }

        try {
            int precio = Integer.parseInt(precioStr);
            ServiciosRequest request = new ServiciosRequest(nombre, precio);

            Call<Void> call = authApiService.crear(request);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        mensaje.setValue("Servicio registrado exitosamente");
                    } else {
                        mensaje.setValue("Error al registrar servicio: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    mensaje.setValue("Error de conexión: " + t.getMessage());
                }
            });
        } catch (NumberFormatException e) {
            mensaje.setValue("Por favor ingresa un precio válido");
        }
    }

    public MutableLiveData<String> getMensaje() {
        return mensaje;
    }
}
