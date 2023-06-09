package com.example.inmobiliariamoviles;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariamoviles.models.Propietario;
import com.example.inmobiliariamoviles.models.Usuario;
import com.example.inmobiliariamoviles.request.ApiClient;
import com.example.inmobiliariamoviles.request.ApiClientRetrofit;
import com.example.inmobiliariamoviles.request.EndpointInmobiliaria;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {

//    Variables
    private Context context;
    private ApiClient apiClient;
    private MutableLiveData<String> errorMsg;
    private MutableLiveData<Boolean> permisoLlamada;
    private LeeSensor leeSensor;
    protected static final int CALL_PERMISSION_REQUEST_CODE = 123;
    private static final String NUMERO_INMOBILIARIA = "2664371603"; // Es mi numero jaja (porfis no me llamen 🥺)

//    Constructor
    public MainViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
//        apiClient = ApiClient.getApi();

//        Directamente verificamos si estan dados los permisos
        permisoLlamada = new MutableLiveData<>();
//        La funcion lo setea en el livedata
        verificarPermisosDeLlamada();

        leeSensor = new LeeSensor(context);
        registerListener();
    }

//    Getters
    public LiveData<String> getErrorMsg() {
        if (errorMsg == null) {
            errorMsg = new MutableLiveData<>();
            errorMsg.setValue("");
        }
        return errorMsg;
    }

    public LiveData<Boolean> getPermisoLlamada() {
        return permisoLlamada;
    }

//    Logeo del usuario
    public void login(String email, String password) {
//        Validar que venga vacio el email y password
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Campo vacio", Toast.LENGTH_SHORT).show();
            errorMsg.setValue("Campo vacio");
            return;
        }

        Usuario user = new Usuario(email, password);
        EndpointInmobiliaria endpoint = ApiClientRetrofit.getEndpoint();
        Call<String> call = endpoint.login(user);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                if (response.isSuccessful()) {

                    if (response.body() != null) {
                        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("token", "Bearer " + response.body());
                        editor.commit();

                        Intent intent = new Intent(context, NavigationActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }
                } else {
                    if (response.code() == 404) {
                        Toast.makeText(context, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error al llamar login", Toast.LENGTH_SHORT).show();
                Log.d("RESPONSE", t.getMessage());
            }
        });
    }

//    Funcion para verificar la sesion
    public void verificarSesion() {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");

        if (token.isEmpty()) { // Si no trae token, volver al login
            return;
        }

        EndpointInmobiliaria endpoint = ApiClientRetrofit.getEndpoint();
        Call<Propietario> call = endpoint.obtenerPerfil(token);

        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(@NonNull Call<Propietario> call, @NonNull Response<Propietario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Intent intent = new Intent(context, NavigationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Propietario> call, @NonNull Throwable t) {
            }
        });
    }

//    Funciones del sensor de movimiento
    public void registerListener() {
        if (leeSensor == null) return;
        leeSensor.registerListener();
    }

    public void unregisterListener() {
        if (leeSensor == null) return;
        leeSensor.unregisterListener();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        unregisterListener();
    }

//    Clase interna para el sensor de movimiento
    private class LeeSensor implements SensorEventListener {

        private SensorManager sensorManager;
        private Sensor accelerometer;
        private float acceleration, currentAcceleration, lastAcceleration;

        private static final float SHAKE_THRESHOLD = 10.0f;

        public LeeSensor(Context context) {
            this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            this.accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            acceleration = 0.0f;
            currentAcceleration = SensorManager.GRAVITY_EARTH;
            lastAcceleration = SensorManager.GRAVITY_EARTH;
        }

        public void registerListener() {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        public void unregisterListener() {
            sensorManager.unregisterListener(this);
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            lastAcceleration = currentAcceleration;
            currentAcceleration = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = currentAcceleration - lastAcceleration;
            acceleration = acceleration * 0.9f + delta;

            if (acceleration > SHAKE_THRESHOLD) {
                MainViewModel.this.llamar();
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    }


//    Funciones de llamada y permisos
    public void verificarPermisosDeLlamada() {
//        Ver si tiene permiso para llamar
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            No dieron permiso, pedirlo
            permisoLlamada.setValue(false);
            return;
        }

//        Tiene permisos, realizar llamada
        permisoLlamada.setValue(true);
    }

    private void llamar() {
//        Si es falso, no tiene permisos para llamar
        if (Boolean.FALSE.equals(permisoLlamada.getValue())) {
            verificarPermisosDeLlamada();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + NUMERO_INMOBILIARIA));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
