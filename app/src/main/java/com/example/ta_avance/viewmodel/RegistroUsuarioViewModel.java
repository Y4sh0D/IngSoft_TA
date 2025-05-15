package com.example.ta_avance.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.ta_avance.api.ApiClient;
import com.example.ta_avance.api.AuthApiService;
import com.example.ta_avance.dto.LoginRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroUsuarioViewModel extends AndroidViewModel {

    private final AuthApiService authApiService;
    public MutableLiveData<Boolean> registroExitoso = new MutableLiveData<>();
    public MutableLiveData<String> mensajeError = new MutableLiveData<>();

    public RegistroUsuarioViewModel(@NonNull Application application) {
        super(application);
        authApiService = ApiClient.getRetrofit(application, true).create(AuthApiService.class);
    }

    public void registrarUsuario(String username, String password, String nombre, String apellido, String correo) {
        if (username.isEmpty() || password.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
            mensajeError.postValue("Por favor completa todos los campos");
            return;
        }

        LoginRequest request = new LoginRequest(username, password, nombre, apellido, correo);

        authApiService.register(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    registroExitoso.postValue(true);
                } else {
                    mensajeError.postValue("Error al registrar usuario: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mensajeError.postValue("Error de conexión: " + t.getMessage());
            }
        });
    }
}
