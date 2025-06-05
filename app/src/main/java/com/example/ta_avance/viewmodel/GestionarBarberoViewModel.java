package com.example.ta_avance.viewmodel;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import com.example.ta_avance.api.ApiClient;
import com.example.ta_avance.api.AuthApiService;
import com.example.ta_avance.dto.barbero.BarberoDto;
import com.example.ta_avance.dto.barbero.BarberoRequest;
import com.example.ta_avance.dto.barbero.BarberoResponse;
import com.example.ta_avance.dto.barbero.BarberoSimpleResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GestionarBarberoViewModel {

    public interface BarberoCallback {
        void onSuccess(List<BarberoDto> barberos);
        void onError(String mensaje);
    }

    public interface BarberoOperacionCallback {
        void onSuccess(String mensaje);
        void onError(String mensaje);
    }

    public interface ActualizarCallback {
        void onSuccess(String mensaje);
        void onError(String mensaje);
    }

    public void obtenerBarberos(Context context, BarberoCallback callback) {
        AuthApiService api = ApiClient.getRetrofit(context, true).create(AuthApiService.class);
        Call<BarberoResponse> call = api.listarBarberos();

        call.enqueue(new Callback<BarberoResponse>() {
            @Override
            public void onResponse(Call<BarberoResponse> call, Response<BarberoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onError("Error al obtener barberos");
                }
            }

            @Override
            public void onFailure(Call<BarberoResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void crearBarbero(Context context, String nombre, Uri imagenUri, BarberoOperacionCallback callback) {
        AuthApiService api = ApiClient.getRetrofit(context, true).create(AuthApiService.class);

        // 1. Serializar el DTO a JSON
        BarberoRequest nuevo = new BarberoRequest(nombre);
        Gson gson = new Gson();
        String json = gson.toJson(nuevo);
        RequestBody dtoBody = RequestBody.create(MediaType.parse("application/json"), json);

        // 2. Preparar imagen si existe
        MultipartBody.Part imagenPart = null;
        if (imagenUri != null) {
            try {
                ContentResolver resolver = context.getContentResolver();
                InputStream inputStream = resolver.openInputStream(imagenUri);

                byte[] imageBytes = new byte[inputStream.available()];
                inputStream.read(imageBytes);
                inputStream.close();

                RequestBody imagenBody = RequestBody.create(
                        MediaType.parse(resolver.getType(imagenUri)),
                        imageBytes// Ej: "image/jpeg"
                );

                String fileName = "imagen.jpg"; // Puedes generar uno dinámico si deseas
                imagenPart = MultipartBody.Part.createFormData("imagen", fileName, imagenBody);
            } catch (IOException e) {
                e.printStackTrace();
                callback.onError("Error al procesar la imagen.");
                return;
            }
        }

        // 3. Llamar a la API
        api.crearBarbero(dtoBody, imagenPart).enqueue(new Callback<BarberoResponse>() {
            @Override
            public void onResponse(Call<BarberoResponse> call, Response<BarberoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMessage());
                } else {
                    callback.onError("Error al crear barbero");
                }
            }

            @Override
            public void onFailure(Call<BarberoResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }


    public void actualizarBarbero(Context context, int id, String nuevoNombre, ActualizarCallback callback) {
        AuthApiService api = ApiClient.getRetrofit(context, true).create(AuthApiService.class);
        BarberoRequest request = new BarberoRequest(nuevoNombre);

        api.actualizarBarbero(id, request).enqueue(new Callback<BarberoSimpleResponse>() {
            @Override
            public void onResponse(Call<BarberoSimpleResponse> call, Response<BarberoSimpleResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMessage());
                } else {
                    callback.onError("Error al actualizar barbero");
                }
            }

            @Override
            public void onFailure(Call<BarberoSimpleResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });

    }

    public void eliminarBarbero(Context context, int id, BarberoOperacionCallback callback) {
        AuthApiService api = ApiClient.getRetrofit(context, true).create(AuthApiService.class);

        api.eliminarBarbero(id).enqueue(new Callback<BarberoResponse>() {
            @Override
            public void onResponse(Call<BarberoResponse> call, Response<BarberoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMessage());
                } else {
                    callback.onError("Error al eliminar barbero");
                }
            }

            @Override
            public void onFailure(Call<BarberoResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
