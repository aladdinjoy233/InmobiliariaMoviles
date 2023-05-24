package com.example.inmobiliariamoviles.ui.logout;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariamoviles.MainActivity;
import com.example.inmobiliariamoviles.NavigationActivity;
import com.example.inmobiliariamoviles.models.Propietario;
import com.example.inmobiliariamoviles.request.ApiClientRetrofit;
import com.example.inmobiliariamoviles.request.EndpointInmobiliaria;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogoutViewModel extends AndroidViewModel {

    private Context context;

    public LogoutViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void logout() {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("token");
        editor.apply();


        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

}