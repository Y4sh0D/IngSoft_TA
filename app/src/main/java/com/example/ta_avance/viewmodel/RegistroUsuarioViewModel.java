package com.example.ta_avance.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.ta_avance.api.ApiClient;
import com.example.ta_avance.api.AuthApiService;
import com.example.ta_avance.dto.horario.GenericResponse;
import com.example.ta_avance.dto.login.LoginRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

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

    public void registrarUsuario(String username, String password, String nombre, String apellido, String correo ,String celular) {
        if (username.isEmpty() || password.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty()|| celular.isEmpty() ) {
            mensajeError.postValue("Por favor completa todos los campos");
            return;
        }

        LoginRequest request = new LoginRequest(username, password, nombre, apellido, correo ,celular);

        authApiService.register(request).enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GenericResponse resp = response.body();
                    if (resp.getStatus() == 200) {
                        registroExitoso.postValue(true);
                    } else {
                        mensajeError.postValue("Error en registro: " + resp.getMessage());
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();

                        // Parsear solo el mensaje
                        JsonObject jsonObject = new Gson().fromJson(errorBody, JsonObject.class);
                        String msg = jsonObject.has("message") ? jsonObject.get("message").getAsString() : "Error desconocido";

                        mensajeError.postValue(msg);

                    } catch (Exception e) {
                        mensajeError.postValue("Error al procesar la respuesta. Código: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                mensajeError.postValue("Error de conexión: " + t.getMessage());
            }
        });
    }
}
