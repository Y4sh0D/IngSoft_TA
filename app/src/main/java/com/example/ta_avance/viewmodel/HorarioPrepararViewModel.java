package com.example.ta_avance.viewmodel;

import android.content.Context;
import android.util.Log;

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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HorarioPrepararViewModel extends ViewModel {

    private final MutableLiveData<List<String>> dias = new MutableLiveData<>();
    private final MutableLiveData<List<String>> turnos = new MutableLiveData<>();
    private final MutableLiveData<List<BarberoDto>> barberos = new MutableLiveData<>();

    public HorarioPrepararViewModel() {
        dias.setValue(Arrays.asList("LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO", "DOMINGO"));
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
    public void guardarTurnosDia(Context context, String dia, Map<Long, List<Long>> turnosPorTipo, Runnable onSuccess, Runnable onError) {
        Log.d("DEBUG_API", "Enviando para día: " + dia + ", turnos: " + new Gson().toJson(turnosPorTipo));

        TurnosDiaRequest request = new TurnosDiaRequest(dia, turnosPorTipo);
        AuthApiService api = ApiClient.getRetrofit(context, true).create(AuthApiService.class);

        api.actualizarTurnosDia(request).enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("DEBUG_API", "Mensaje: " + response.body().getMessage());
                    onSuccess.run();
                } else {
                    Log.e("DEBUG_API", "Error al guardar: " + response.code());
                    onError.run();
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Log.e("DEBUG_API", "Falló conexión: ", t);
                onError.run();
            }
        });

    }

}
