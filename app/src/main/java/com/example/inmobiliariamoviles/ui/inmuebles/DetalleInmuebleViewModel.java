package com.example.inmobiliariamoviles.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariamoviles.MainActivity;
import com.example.inmobiliariamoviles.models.Inmueble;
import com.example.inmobiliariamoviles.models.Propietario;
import com.example.inmobiliariamoviles.request.ApiClientRetrofit;
import com.example.inmobiliariamoviles.request.EndpointInmobiliaria;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> inmueble = new MutableLiveData<>();
    private MutableLiveData<Boolean> estadoInmueble = new MutableLiveData<>();
    private Context context;

    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Inmueble> getInmueble() { return inmueble; }

    public LiveData<Boolean> getEstadoInmueble() { return estadoInmueble; }

    public void procesarDatos(Bundle bundle) {
        if (bundle == null) {
            this.inmueble.setValue(null);
            return;
        }

        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
        if (inmueble != null) {
            this.inmueble.setValue(inmueble);
            estadoInmueble.setValue(inmueble.isActivo());
        }
    }

    public void setEstado(boolean estado) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");

        if (token.isEmpty()) { // Si no trae token, volver al login
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            return;
        }

        EndpointInmobiliaria endpoint = ApiClientRetrofit.getEndpoint();
        Call<Inmueble> call = endpoint.cambiarEstado(token, inmueble.getValue().getId_Inmueble(), estado);

        call.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(@NonNull Call<Inmueble> call, @NonNull Response<Inmueble> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        inmueble.setValue(response.body());
                        estadoInmueble.setValue(response.body().isActivo());
                        Toast.makeText(context, "Estado modificado exitosamente", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Inmueble> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error al obtener usuario", Toast.LENGTH_SHORT).show();
            }
        });

    }

}