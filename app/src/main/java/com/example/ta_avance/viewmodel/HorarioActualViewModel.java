package com.example.ta_avance.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ta_avance.api.ApiClient;
import com.example.ta_avance.api.AuthApiService;
import com.example.ta_avance.dto.horario.HorarioInstanciaResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HorarioActualViewModel extends ViewModel {
    private final MutableLiveData<Map<String, Map<String, List<String>>>> horarios = new MutableLiveData<>();

    public LiveData<Map<String, Map<String, List<String>>>> getHorarios() {
        return horarios;
    }

    public void cargarHorarios(Context context) {
        AuthApiService api = ApiClient.getRetrofit(context, true).create(AuthApiService.class);

        Call<Map<String, List<HorarioInstanciaResponse>>> call = api.obtenerHorarioActual();
        call.enqueue(new Callback<Map<String, List<HorarioInstanciaResponse>>>() {
            @Override
            public void onResponse(Call<Map<String, List<HorarioInstanciaResponse>>> call, Response<Map<String, List<HorarioInstanciaResponse>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, Map<String, List<String>>> semana = new HashMap<>();

                    for (Map.Entry<String, List<HorarioInstanciaResponse>> entry : response.body().entrySet()) {
                        String dia = entry.getKey();
                        List<HorarioInstanciaResponse> turnosList = entry.getValue();

                        Map<String, List<String>> turnos = new HashMap<>();

                        for (HorarioInstanciaResponse turno : turnosList) {
                            String tipo = turno.getTipoHorario();
                            String barbero = turno.getBarbero();

                            if (!turnos.containsKey(tipo)) {
                                turnos.put(tipo, new ArrayList<>());
                            }
                            turnos.get(tipo).add(barbero);
                        }

                        semana.put(dia, turnos);
                    }

                    horarios.postValue(semana);

                }
            }

            @Override
            public void onFailure(Call<Map<String, List<HorarioInstanciaResponse>>> call, Throwable t) {
                t.printStackTrace();
                // Aquí podrías notificar un error si lo deseas
            }
        });
    }
}
