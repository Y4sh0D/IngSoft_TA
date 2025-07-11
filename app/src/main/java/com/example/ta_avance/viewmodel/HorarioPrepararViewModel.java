package com.example.ta_avance.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ta_avance.api.ApiClient;
import com.example.ta_avance.api.AuthApiService;
import com.example.ta_avance.dto.barbero.BarberoDto;
import com.example.ta_avance.dto.barbero.BarberoResponse;
import com.example.ta_avance.dto.horario.GenericResponse;
import com.example.ta_avance.dto.horario.TurnosDiaRequest;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HorarioPrepararViewModel extends ViewModel {

    private final MutableLiveData<List<String>> dias = new MutableLiveData<>();
    private final MutableLiveData<List<String>> turnos = new MutableLiveData<>();
    private final MutableLiveData<List<BarberoDto>> barberos = new MutableLiveData<>();

    public HorarioPrepararViewModel() {
        dias.setValue(Arrays.asList("LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES", "SÁBADO", "DOMINGO"));
        turnos.setValue(Arrays.asList("MAÑANA", "TARDE", "NOCHE")); // o ajusta a tus turnos reales
    }

    public LiveData<List<String>> getDias() {
        return dias;
    }

    public LiveData<List<String>> getTurnos() {
        return turnos;
    }

    public LiveData<List<BarberoDto>> getBarberos() {
        return barberos;
    }

    public void cargarBarberos(Context context) {
        AuthApiService api = ApiClient.getRetrofit(context, true).create(AuthApiService.class);
        api.listarBarberos().enqueue(new Callback<BarberoResponse>() {
            @Override
            public void onResponse(Call<BarberoResponse> call, Response<BarberoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    barberos.postValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<BarberoResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * Convierte la selección a Map<String, List<Integer>> para TurnosDiaRequest
     * y envía la petición al backend
     */
    public void guardarTurnosDia(Context context, String dia, Map<Long, List<Long>> turnosPorTipo) {
        Log.d("DEBUG_API", "Enviando para día: " + dia + ", turnos: " + new Gson().toJson(turnosPorTipo));

        TurnosDiaRequest request = new TurnosDiaRequest(dia, turnosPorTipo);
        AuthApiService api = ApiClient.getRetrofit(context, true).create(AuthApiService.class);

        api.actualizarTurnosDia(request).enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("DEBUG_API", "Mensaje: " + response.body().getMessage());
                    Toast.makeText(context, response.body().getMessage() + " para el día " + dia.toLowerCase(), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String errorMsg = response.errorBody() != null ? response.errorBody().string() : "Error desconocido";
                        Log.e("DEBUG_API", "Error: " + errorMsg);
                        Toast.makeText(context, "Error al guardar turnos", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(context, "Error inesperado", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Log.e("DEBUG_API", "Falló conexión: ", t);
                Toast.makeText(context, "Error de red al guardar turnos", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void confirmarHorario(Context context) {
        AuthApiService api = ApiClient.getRetrofit(context, true).create(AuthApiService.class);
        api.confirmarHorario().enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        // Intentar leer el mensaje del cuerpo si es un error controlado
                        String errorMsg = response.errorBody() != null ? response.errorBody().string() : "Error desconocido";
                        Log.e("DEBUG_API", "Error body: " + errorMsg);
                        Toast.makeText(context, "Error al confirmar horario", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(context, "Error inesperado", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Log.e("DEBUG_API", "Falló la conexión al confirmar: ", t);
                Toast.makeText(context, "Error de red al confirmar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void exportarHorario(Context context) {
        LocalDate hoy = LocalDate.now();
        LocalDate proximoLunes = hoy.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDate proximoDomingo = proximoLunes.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        AuthApiService apiService = ApiClient.getRetrofit(context, true).create(AuthApiService.class);
        apiService.exportarHorario(proximoLunes, proximoDomingo).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        File file = new File(context.getExternalFilesDir(null), "horario_barbero.pdf");
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(response.body().bytes());
                        fos.close();

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file), "application/pdf");
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        context.startActivity(intent);
                    } catch (IOException e) {
                        Toast.makeText(context, "Error al guardar el PDF", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "No se pudo generar el PDF", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}