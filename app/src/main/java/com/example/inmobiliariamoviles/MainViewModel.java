package com.example.inmobiliariamoviles;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
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
import com.example.inmobiliariamoviles.request.ApiClient;

public class MainViewModel extends AndroidViewModel {

    private Context context;
    private ApiClient apiClient;
    private MutableLiveData<String> errorMsg;
    private MutableLiveData<Boolean> permisoLlamada;

    private LeeSensor leeSensor;
    protected static final int CALL_PERMISSION_REQUEST_CODE = 123;
    private static final String NUMERO_INMOBILIARIA = "2664371603"; // Es mi numero jaja

    public MainViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        apiClient = ApiClient.getApi();

        leeSensor = new LeeSensor(context);
        registerListener();
    }

    public LiveData<String> getErrorMsg() {
        if (errorMsg == null) {
            errorMsg = new MutableLiveData<>();
            errorMsg.setValue("");
        }
        return errorMsg;
    }

    public LiveData<Boolean> getPermisoLlamada() {
        if (permisoLlamada == null) {
            permisoLlamada = new MutableLiveData<>();
            permisoLlamada.setValue(false);
        }
        return permisoLlamada;
    }

    public void login(String email, String password) {
//        Validar que venga vacio el email y password
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Campo vacio", Toast.LENGTH_SHORT).show();
            errorMsg.setValue("Campo vacio");
            return;
        }

        Propietario propietario = apiClient.login(email, password);

//        Si no existe el usuario, algun dato esta incorrecto
        if (propietario == null) {
            Toast.makeText(context, "Datos incorrectos", Toast.LENGTH_SHORT).show();
            errorMsg.setValue("Datos incorrectos");
            return;
        }

        Toast.makeText(context, "Login exitosa", Toast.LENGTH_SHORT).show();
        errorMsg.setValue("");
//        TODO: Go to the main navigation activity using intents and all that
    }

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
        if (Boolean.FALSE.equals(permisoLlamada.getValue())) {
            verificarPermisosDeLlamada();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + NUMERO_INMOBILIARIA));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

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
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}
