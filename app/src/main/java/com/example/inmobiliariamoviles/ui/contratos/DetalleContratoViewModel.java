package com.example.inmobiliariamoviles.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.example.inmobiliariamoviles.MainActivity;
import com.example.inmobiliariamoviles.R;
import com.example.inmobiliariamoviles.models.Contrato;
import com.example.inmobiliariamoviles.models.Inmueble;
import com.example.inmobiliariamoviles.models.Inquilino;
import com.example.inmobiliariamoviles.request.ApiClientRetrofit;
import com.example.inmobiliariamoviles.request.EndpointInmobiliaria;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {
    private MutableLiveData<Contrato> contrato = new MutableLiveData<>();
    private Context context;

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Contrato> getContrato() { return contrato; }

    public void procesarDatos(Bundle bundle) {
        if (bundle == null) {
            this.contrato.setValue(null);
            return;
        }

        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
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
        Call<Contrato> call = endpoint.obtenerContratoPorInmueble(token, inmueble.getId_Inmueble());

        call.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(@NonNull Call<Contrato> call, @NonNull Response<Contrato> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        contrato.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Contrato> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error al obtener contrato", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void verPagos(View v) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("contrato", contrato.getValue());
        Navigation.findNavController(v).navigate(R.id.pagos_fragment, bundle);
    }
}