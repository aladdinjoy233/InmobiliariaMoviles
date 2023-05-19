package com.example.inmobiliariamoviles.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariamoviles.models.Propietario;
import com.example.inmobiliariamoviles.request.ApiClient;

public class PerfilViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Propietario> propietario;
    private MutableLiveData<Boolean> isEdit;
    ApiClient apiClient;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();

        apiClient = ApiClient.getApi();

        propietario = new MutableLiveData<>();
        cargarDatosUsuario();
    }

    public LiveData<Propietario> getPropietario() { return propietario; }

    public LiveData<Boolean> getIsEdit() {
        if (isEdit == null) {
            isEdit = new MutableLiveData<>();
            isEdit.setValue(false);
        }
        return isEdit;
    }

    public void cargarDatosUsuario() {
        Propietario usuario = apiClient.obtenerUsuarioActual();

        if (usuario == null) {
            Toast.makeText(context, "Error al obtener usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        propietario.setValue(usuario);
    }

    public void cambiarEstadoEdit() {
        isEdit.setValue(!isEdit.getValue());
    }

    public void guardarPerfil(Long dni, String nombre, String apellido, String email, String contrasena, String telefono) {
        Propietario usuarioActual = new Propietario(propietario.getValue().getId(), dni, nombre, apellido, email, contrasena, telefono, propietario.getValue().getAvatar());
        apiClient.actualizarPerfil(usuarioActual);
        propietario.setValue(usuarioActual);
        cambiarEstadoEdit();
    }


}