package com.example.inmobiliariamoviles.ui.inmuebles;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariamoviles.models.Inmueble;
import com.example.inmobiliariamoviles.request.ApiClient;

import java.text.NumberFormat;
import java.util.Locale;

public class DetalleInmuebleViewModel extends ViewModel {

    private ApiClient apiClient;
    private MutableLiveData<Inmueble> inmueble = new MutableLiveData<>();
    private MutableLiveData<String> precioFormateada = new MutableLiveData<>();

    public DetalleInmuebleViewModel() {
        apiClient = ApiClient.getApi();
    }

    public LiveData<Inmueble> getInmueble() { return inmueble; }
    public LiveData<String> getPrecioFormateada() { return precioFormateada; }

    public void procesarDatos(Bundle bundle) {
        if (bundle == null) {
            this.inmueble.setValue(null);
            return;
        }

        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
        if (inmueble != null) {
            this.inmueble.setValue(inmueble);
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
            String formattedNumber = numberFormat.format(inmueble.getPrecio());
            this.precioFormateada.setValue(formattedNumber);
        }
    }

    public void setEstado(boolean estado) {
        this.inmueble.getValue().setEstado(estado);
        apiClient.actualizarInmueble(this.inmueble.getValue());
    }

}