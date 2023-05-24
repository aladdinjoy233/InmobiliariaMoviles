package com.example.inmobiliariamoviles.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariamoviles.MainActivity;
import com.example.inmobiliariamoviles.models.Inmueble;
import com.example.inmobiliariamoviles.request.ApiClientRetrofit;
import com.example.inmobiliariamoviles.request.EndpointInmobiliaria;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Inmueble>> inmuebles;
    private Context context;

    public ContratosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        inmuebles = new MutableLiveData<>();
        obtenerInmuebles();
    }

    public LiveData<ArrayList<Inmueble>> getInmuebles() { return inmuebles; }

    private void obtenerInmuebles() {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");

        if (token.isEmpty()) { // Si no trae token, volver al login
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            return;
        }

        EndpointInmobiliaria endpoint = ApiClientRetrofit.getEndpoint();
        Call<List<Inmueble>> call = endpoint.obtenerInmueblesAlquiladas(token);

        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(@NonNull Call<List<Inmueble>> call, @NonNull Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        inmuebles.setValue((ArrayList<Inmueble>) response.body());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Inmueble>> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error al obtener usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }

}