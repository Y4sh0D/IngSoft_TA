package com.example.ta_avance.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ta_avance.api.ApiClient;
import com.example.ta_avance.api.AuthApiService;
import com.example.ta_avance.dto.horario.HorarioInstanciaResponse;
import com.example.ta_avance.dto.horario.HorarioResponseWrapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
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

        Call<HorarioResponseWrapper> call = api.obtenerHorarioActual();
        call.enqueue(new Callback<HorarioResponseWrapper>() {
            @Override
            public void onResponse(Call<HorarioResponseWrapper> call, Response<HorarioResponseWrapper> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, List<HorarioInstanciaResponse>> datos = response.body().getData();
                    Map<String, Map<String, List<String>>> semana = new HashMap<>();

                    for (Map.Entry<String, List<HorarioInstanciaResponse>> entry : datos.entrySet()) {
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
            public void onFailure(Call<HorarioResponseWrapper> call, Throwable t) {
                t.printStackTrace();
                // Aquí podrías notificar un error si lo deseas
            }
        });
    }

    public void exportarHorario(Context context) {
        LocalDate hoy = LocalDate.now();

        // Obtener el lunes de esta semana (puede ser hoy si hoy es lunes)
        LocalDate lunes = hoy.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        // Obtener el domingo de esta semana (o el próximo si hoy es domingo)
        LocalDate domingo = hoy.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        AuthApiService apiService = ApiClient.getRetrofit(context, true).create(AuthApiService.class);
        apiService.exportarHorario(lunes, domingo).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        File file = new File(context.getExternalFilesDir(null), "horario_barbero.pdf");
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(response.body().bytes());
                        fos.close();

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(
                                FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file),
                                "application/pdf"
                        );
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
