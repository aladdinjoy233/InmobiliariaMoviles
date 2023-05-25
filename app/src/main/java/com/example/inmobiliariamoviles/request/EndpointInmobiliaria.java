package com.example.inmobiliariamoviles.request;

import com.example.inmobiliariamoviles.models.Contrato;
import com.example.inmobiliariamoviles.models.Inmueble;
import com.example.inmobiliariamoviles.models.Inquilino;
import com.example.inmobiliariamoviles.models.Pago;
import com.example.inmobiliariamoviles.models.Propietario;
import com.example.inmobiliariamoviles.models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EndpointInmobiliaria {

    @POST("propietarios/login")
    Call<String> login(@Body Usuario usuario);

    @GET("propietarios/perfil")
    Call<Propietario> obtenerPerfil(@Header("Authorization") String token);

    @POST("propietarios/edit")
    Call<Propietario> editarPerfil(@Header("Authorization") String token, @Body Propietario propietario);

    @GET("inmuebles/")
    Call<List<Inmueble>> obtenerInmuebles(@Header("Authorization") String token);

    @PUT("inmuebles/cambiar_estado/{id}")
    Call<Inmueble> cambiarEstado(@Header("Authorization") String token, @Path("id") int id, @Body boolean estado);

    @GET("inmuebles/alquilados")
    Call<List<Inmueble>> obtenerInmueblesAlquiladas(@Header("Authorization") String token);

    @GET("Inquilinos/Obtener/{id}")
    Call<Inquilino> obtenerInquilinoPorInmueble(@Header("Authorization") String token, @Path("id") int id);

    @GET("Contratos/Obtener/{id}")
    Call<Contrato> obtenerContratoPorInmueble(@Header("Authorization") String token, @Path("id") int id);

    @GET("Pagos/Obtener/{id}")
    Call<List<Pago>> obtenerPagosPorContrato(@Header("Authorization") String token, @Path("id") int id);


}
