package com.example.ta_avance.viewmodel;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import com.example.ta_avance.api.ApiClient;
import com.example.ta_avance.api.AuthApiService;
import com.example.ta_avance.dto.servicio.ServicioDto;
import com.example.ta_avance.dto.servicio.ServicioRequest;
import com.example.ta_avance.dto.servicio.ServicioResponse;
import com.example.ta_avance.dto.servicio.ServicioSimpleResponse;
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

public class GestionarServicioViewModel {

    public interface ServicioCallback {
        void onSuccess(List<ServicioDto> servicios);
        void onError(String mensaje);
    }

    public interface ServicioOperacionCallback {
        void onSuccess(String mensaje);
        void onError(String mensaje);
    }

    public interface ActualizarCallback {
        void onSuccess(String mensaje);
        void onError(String mensaje);
    }

    public void obtenerServicios(Context context, ServicioCallback callback) {
        AuthApiService api = ApiClient.getRetrofit(context, true).create(AuthApiService.class);
        Call<ServicioResponse> call = api.listarServicios();

        call.enqueue(new Callback<ServicioResponse>() {
            @Override
            public void onResponse(Call<ServicioResponse> call, Response<ServicioResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onError("Error al obtener servicios");
                }
            }

            @Override
            public void onFailure(Call<ServicioResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void crearServicio(Context context, ServicioRequest request, Uri imagenUri, ServicioOperacionCallback callback) {
        AuthApiService api = ApiClient.getRetrofit(context, true).create(AuthApiService.class);

        Gson gson = new Gson();
        String json = gson.toJson(request);
        RequestBody dtoServicio = RequestBody.create(MediaType.parse("application/json"),json);

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

        api.crearServicio(dtoServicio,imagenPart).enqueue(new Callback<ServicioResponse>() {
            @Override
            public void onResponse(Call<ServicioResponse> call, Response<ServicioResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMessage());
                } else {
                    callback.onError("Error al crear servicio");
                }
            }

            @Override
            public void onFailure(Call<ServicioResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void actualizarServicio(Context context, int id,ServicioRequest request, Uri imagenUri , ActualizarCallback callback){
        AuthApiService api = ApiClient.getRetrofit(context,true).create(AuthApiService.class);

        Gson gson = new Gson();
        String json = gson.toJson(request);
        RequestBody dtoServicio = RequestBody.create(MediaType.parse("application/json"),json);

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

        api.actualizarServicio(id, dtoServicio,imagenPart).enqueue(new Callback<ServicioSimpleResponse>() {
            @Override
            public void onResponse(Call<ServicioSimpleResponse> call, Response<ServicioSimpleResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMessage());
                } else {
                    callback.onError("Error al actualizar servicio");
                }
            }

            @Override
            public void onFailure(Call<ServicioSimpleResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void eliminarServicio(Context context, int id, ServicioOperacionCallback callback) {
        AuthApiService api = ApiClient.getRetrofit(context, true).create(AuthApiService.class);

        api.eliminarServicio(id).enqueue(new Callback<ServicioResponse>() {
            @Override
            public void onResponse(Call<ServicioResponse> call, Response<ServicioResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMessage());
                } else {
                    callback.onError("Error al eliminar servicio");
                }
            }

            @Override
            public void onFailure(Call<ServicioResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
