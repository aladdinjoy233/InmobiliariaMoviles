package com.example.inmobiliariamoviles;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.inmobiliariamoviles.models.Propietario;
import com.example.inmobiliariamoviles.request.ApiClient;

public class MainViewModel extends AndroidViewModel {

    private Context context;
    private ApiClient apiClient;

    public MainViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        apiClient = ApiClient.getApi();
    }

    public void login(String email, String password) {
        Propietario propietario = apiClient.login(email, password);

        if (propietario == null) {
            Toast.makeText(context, "Datos incorrectos", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(context, "Login exitosa", Toast.LENGTH_SHORT).show();
//        TODO: Go to the main navigation activity using intents and all that
    }
}
