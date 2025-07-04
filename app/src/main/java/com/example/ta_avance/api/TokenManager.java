// com.example.ta_avance.api.TokenManager.java
package com.example.ta_avance.api;

import android.content.Context;

import com.example.ta_avance.dto.login.LoginDataSimpleResponse;
import com.example.ta_avance.dto.refresh.RefreshRequest;
import com.example.ta_avance.util.PreferenciasHelper;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TokenManager {

    private final PreferenciasHelper prefs;
    private final AuthApiService authApi;

    public TokenManager(Context context) {
        prefs = new PreferenciasHelper(context);
        Retrofit retrofit = ApiClient.getRetrofit(context, false);
        authApi = retrofit.create(AuthApiService.class);
    }

    public synchronized String refreshToken() throws IOException {
        String currentRefresh = prefs.obtenerRefreshToken();
        if (currentRefresh == null) return null;

        RefreshRequest request = new RefreshRequest(currentRefresh);
        Call<LoginDataSimpleResponse> call = authApi.refresh(request);
        Response<LoginDataSimpleResponse> response = call.execute();

        if (response.isSuccessful() && response.body() != null) {
            String newToken = response.body().getData().getToken();
            String newRefresh = response.body().getData().getRefreshToken();

            prefs.guardarToken(newToken);
            prefs.guardarRefreshToken(newRefresh);

            return newToken;
        }

        return null;
    }
}
