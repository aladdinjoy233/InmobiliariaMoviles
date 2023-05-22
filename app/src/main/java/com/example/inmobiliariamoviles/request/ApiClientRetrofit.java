package com.example.inmobiliariamoviles.request;

import com.example.inmobiliariamoviles.models.Inmueble;
import com.example.inmobiliariamoviles.models.Propietario;
import com.example.inmobiliariamoviles.models.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public class ApiClientRetrofit {

    private static final String BASE_URL = "http://192.168.1.108:5200/";
    private static EndpointInmobiliaria endpoint;

    public static EndpointInmobiliaria getEndpoint() {

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        endpoint = retrofit.create(EndpointInmobiliaria.class);
        return endpoint;
    }
}
