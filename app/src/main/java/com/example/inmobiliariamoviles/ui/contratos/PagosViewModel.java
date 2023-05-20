package com.example.inmobiliariamoviles.ui.contratos;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariamoviles.models.Contrato;
import com.example.inmobiliariamoviles.models.Inmueble;
import com.example.inmobiliariamoviles.models.Pago;
import com.example.inmobiliariamoviles.request.ApiClient;

import java.util.ArrayList;

public class PagosViewModel extends ViewModel {

    private ApiClient apiClient;
    private MutableLiveData<ArrayList<Pago>> pagos = new MutableLiveData<>();

    public PagosViewModel() { apiClient = ApiClient.getApi(); }

    public LiveData<ArrayList<Pago>> getPagos() { return pagos; }

    public void procesarDatos(Bundle bundle) {
        if (bundle == null) {
            return;
        }

        Contrato contrato = (Contrato) bundle.getSerializable("contrato");
        if (contrato == null) return;

        ArrayList<Pago> pagos = apiClient.obtenerPagos(contrato);
        this.pagos.setValue(pagos);
    }
}