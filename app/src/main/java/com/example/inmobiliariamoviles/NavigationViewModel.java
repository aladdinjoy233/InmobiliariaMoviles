package com.example.inmobiliariamoviles;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariamoviles.models.Propietario;
import com.example.inmobiliariamoviles.request.ApiClient;

public class NavigationViewModel extends AndroidViewModel {

    private Context context;
    private ApiClient apiClient;
    MutableLiveData<Propietario> propietario = new MutableLiveData<>();

    public NavigationViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        apiClient = ApiClient.getApi();

        obtenerPropietario();
    }

    public LiveData<Propietario> getPropietario() {
        return propietario;
    }

    public void obtenerPropietario() {
        Propietario propietario = apiClient.obtenerUsuarioActual();
        if (propietario == null) {
            Toast.makeText(context, "Error al obtener usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        this.propietario.setValue(propietario);
    }
}
