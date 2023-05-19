package com.example.inmobiliariamoviles.ui.inquilinos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariamoviles.models.Inmueble;
import com.example.inmobiliariamoviles.request.ApiClient;

import java.util.ArrayList;

public class InquilinosViewModel extends ViewModel {

    private ApiClient apiClient;
    private MutableLiveData<ArrayList<Inmueble>> inmuebles;

    public InquilinosViewModel() {
        apiClient = ApiClient.getApi();

        inmuebles = new MutableLiveData<>();
        obtenerInmuebles();
    }

    public LiveData<ArrayList<Inmueble>> getInmuebles() { return inmuebles; }

    private void obtenerInmuebles() {
        ArrayList<Inmueble> propiedades = apiClient.obtenerPropiedadesAlquiladas();
        inmuebles.setValue(propiedades);
    }

}