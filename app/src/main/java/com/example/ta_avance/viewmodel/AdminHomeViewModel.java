package com.example.ta_avance.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.ta_avance.util.PreferenciasHelper;

public class AdminHomeViewModel extends AndroidViewModel {

    public final MutableLiveData<String> nombreCompleto = new MutableLiveData<>();
    private final PreferenciasHelper preferenciasHelper;

    public AdminHomeViewModel(@NonNull Application application) {
        super(application);
        preferenciasHelper = new PreferenciasHelper(application);
    }

    public void setNombreYApellido(String nombre, String apellido) {
        nombreCompleto.setValue("Hola " + nombre + " " + apellido);
    }

    public void cerrarSesion() {
        preferenciasHelper.limpiarPreferencias();
    }
}
