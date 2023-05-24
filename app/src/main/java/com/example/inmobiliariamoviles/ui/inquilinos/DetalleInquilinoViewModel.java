package com.example.inmobiliariamoviles.ui.inquilinos;

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
import com.example.inmobiliariamoviles.models.Inquilino;
import com.example.inmobiliariamoviles.request.ApiClientRetrofit;
import com.example.inmobiliariamoviles.request.EndpointInmobiliaria;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInquilinoViewModel extends AndroidViewModel {

    private MutableLiveData<Inquilino> inquilino = new MutableLiveData<>();
    private Context context;

    public DetalleInquilinoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Inquilino> getInquilino() { return inquilino; }

    public void procesarDatos(Bundle bundle) {
        if (bundle == null) {
            this.inquilino.setValue(null);
            return;
        }

        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
        Log.d("Salida: ", "Inmueble: " + inmueble.toString());
        if (inmueble == null) return;

        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");

        if (token.isEmpty()) { // Si no trae token, volver al login
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            return;
        }

        EndpointInmobiliaria endpoint = ApiClientRetrofit.getEndpoint();
        Call<Inquilino> call = endpoint.obtenerPorInmueble(token, inmueble.getId_Inmueble());

        call.enqueue(new Callback<Inquilino>() {
            @Override
            public void onResponse(@NonNull Call<Inquilino> call, @NonNull Response<Inquilino> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        inquilino.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Inquilino> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error al inquilino", Toast.LENGTH_SHORT).show();
            }
        });
    }

}