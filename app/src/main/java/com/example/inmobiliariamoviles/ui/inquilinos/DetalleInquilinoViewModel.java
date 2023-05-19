package com.example.inmobiliariamoviles.ui.inquilinos;

import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariamoviles.models.Inmueble;
import com.example.inmobiliariamoviles.models.Inquilino;
import com.example.inmobiliariamoviles.request.ApiClient;

public class DetalleInquilinoViewModel extends ViewModel {

    private ApiClient apiClient;
    private MutableLiveData<Inquilino> inquilino = new MutableLiveData<>();

    public DetalleInquilinoViewModel() { apiClient = ApiClient.getApi(); }

    public LiveData<Inquilino> getInquilino() { return inquilino; }

    public void procesarDatos(Bundle bundle) {
        if (bundle == null) {
            this.inquilino.setValue(null);
            return;
        }

        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
        if (inmueble == null) return;

        Inquilino inquilino = apiClient.obtenerInquilino(inmueble);
        this.inquilino.setValue(inquilino);
    }

}