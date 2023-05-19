package com.example.inmobiliariamoviles.ui.contratos;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariamoviles.models.Contrato;
import com.example.inmobiliariamoviles.models.Inmueble;
import com.example.inmobiliariamoviles.request.ApiClient;

public class DetalleContratoViewModel extends ViewModel {

    private ApiClient apiClient;
    private MutableLiveData<Contrato> contrato = new MutableLiveData<>();

    public DetalleContratoViewModel() { apiClient = ApiClient.getApi(); }

    public LiveData<Contrato> getContrato() { return contrato; }

    public void procesarDatos(Bundle bundle) {
        if (bundle == null) {
            this.contrato.setValue(null);
            return;
        }

        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
        if (inmueble == null) return;
    }
}