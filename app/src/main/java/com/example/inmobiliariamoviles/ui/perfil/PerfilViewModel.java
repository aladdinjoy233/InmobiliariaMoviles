package com.example.inmobiliariamoviles.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariamoviles.MainActivity;
import com.example.inmobiliariamoviles.models.Propietario;
import com.example.inmobiliariamoviles.request.ApiClientRetrofit;
import com.example.inmobiliariamoviles.request.EndpointInmobiliaria;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Propietario> propietario;
    private MutableLiveData<Boolean> isEdit;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();

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
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");

        if (token.isEmpty()) { // Si no trae token, volver al login
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            return;
        }

        EndpointInmobiliaria endpoint = ApiClientRetrofit.getEndpoint();
        Call<Propietario> call = endpoint.obtenerPerfil(token);

        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(@NonNull Call<Propietario> call, @NonNull Response<Propietario> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        propietario.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Propietario> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error al obtener usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cambiarEstadoEdit() {
        isEdit.setValue(!isEdit.getValue());
    }

    public void guardarPerfil(String dni, String nombre, String apellido, String correo, String telefono) {
        if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(context, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        Propietario usuarioActual = new Propietario(Objects.requireNonNull(propietario.getValue()).getId_Propietario(), dni, nombre, apellido, correo, telefono);

        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");

        if (token.isEmpty()) { // Si no trae token, volver al login
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            return;
        }

        EndpointInmobiliaria endpoint = ApiClientRetrofit.getEndpoint();
        Call<Propietario> call = endpoint.editarPerfil(token, usuarioActual);

        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(@NonNull Call<Propietario> call, @NonNull Response<Propietario> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        propietario.setValue(usuarioActual);
                        Toast.makeText(context, "Editado correctamente", Toast.LENGTH_SHORT).show();
                        cambiarEstadoEdit();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Propietario> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error al obtener usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }


}