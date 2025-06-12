package com.example.ta_avance.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ta_avance.api.ApiClient;
import com.example.ta_avance.api.AuthApiService;
import com.example.ta_avance.dto.rangos.RangoDto;
import com.example.ta_avance.dto.rangos.RangoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservasViewModel extends ViewModel {

    private MutableLiveData<List<RangoDto>> rangosLiveData = new MutableLiveData<>();

    public LiveData<List<RangoDto>> getRangosLiveData(){
        return rangosLiveData;
    }

    public void obtenerRangos(Context context){
        AuthApiService api = ApiClient.getRetrofit(context, true).create(AuthApiService.class);
        Call<RangoResponse> call = api.listarRangos();

        call.enqueue(new Callback<RangoResponse>() {
            @Override
            public void onResponse(Call<RangoResponse> call, Response<RangoResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    rangosLiveData.setValue(response.body().getData());
                } else {
                    Toast.makeText(context, "Error al obtener los rangos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RangoResponse> call, Throwable t) {
                Toast.makeText(context, "Fallo de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
