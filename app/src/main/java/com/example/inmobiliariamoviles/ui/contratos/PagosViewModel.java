package com.example.inmobiliariamoviles.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariamoviles.MainActivity;
import com.example.inmobiliariamoviles.models.Contrato;
import com.example.inmobiliariamoviles.models.Inmueble;
import com.example.inmobiliariamoviles.models.Pago;
import com.example.inmobiliariamoviles.request.ApiClientRetrofit;
import com.example.inmobiliariamoviles.request.EndpointInmobiliaria;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Pago>> pagos = new MutableLiveData<>();
    private Context context;

    public PagosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<ArrayList<Pago>> getPagos() { return pagos; }

    public void procesarDatos(Bundle bundle) {
        if (bundle == null) {
            return;
        }

        Contrato contrato = (Contrato) bundle.getSerializable("contrato");
        if (contrato == null) return;

        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");

        if (token.isEmpty()) { // Si no trae token, volver al login
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            return;
        }

        EndpointInmobiliaria endpoint = ApiClientRetrofit.getEndpoint();
        Call<List<Pago>> call = endpoint.obtenerPagosPorContrato(token, contrato.getId_Contrato());

        call.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(@NonNull Call<List<Pago>> call, @NonNull Response<List<Pago>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        pagos.setValue((ArrayList<Pago>) response.body());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Pago>> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error al obtener usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }
}