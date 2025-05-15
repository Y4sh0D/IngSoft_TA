package com.example.ta_avance.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.auth0.android.jwt.JWT;
import com.example.ta_avance.api.AuthApiService;
import com.example.ta_avance.dto.LoginRequest;
import com.example.ta_avance.dto.LoginResponse;
import com.example.ta_avance.util.PreferenciasHelper;
import com.example.ta_avance.api.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {

    private final AuthApiService authApiService;
    public final MutableLiveData<String> loginStatus = new MutableLiveData<>();
    public final MutableLiveData<String> nombre = new MutableLiveData<>();
    public final MutableLiveData<String> apellido = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        authApiService = ApiClient.getRetrofit(application, false).create(AuthApiService.class);
    }

    public void login(String usuario, String contraseña) {
        LoginRequest request = new LoginRequest(usuario, contraseña);
        Call<LoginResponse> call = authApiService.login(request);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getData().getToken();
                    String refreshToken = response.body().getData().getRefreshToken();
                    JWT jwt = new JWT(token);
                    String role = jwt.getClaim("rol").asString();

                    if (!"ADMIN".equals(role)) {
                        loginStatus.postValue("NO_ADMIN");
                        return;
                    }

                    PreferenciasHelper prefs = new PreferenciasHelper(getApplication());
                    prefs.guardarToken(token);
                    prefs.guardarRefreshToken(refreshToken);


                    nombre.postValue(jwt.getClaim("nombre").asString());
                    apellido.postValue(jwt.getClaim("apellido").asString());

                    loginStatus.postValue("SUCCESS");
                } else if (response.code() == 401) {
                    loginStatus.postValue("INVALID");
                } else {
                    loginStatus.postValue("ERROR_" + response.code());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginStatus.postValue("FAILURE: " + t.getMessage());
            }
        });
    }
}
