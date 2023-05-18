package com.example.inmobiliariamoviles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.inmobiliariamoviles.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);

        viewModel.getErrorMsg().observe(this, errorMsg -> binding.tvError.setText(errorMsg));

        binding.btLogin.setOnClickListener(view -> viewModel.login(binding.etEmail.getText().toString(), binding.etPassword.getText().toString()));

        viewModel.getPermisoLlamada().observe(this, permisoLlamada -> {
            if (!permisoLlamada) {
                pedirPermisos();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.registerListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.unregisterListener();
    }

    private void pedirPermisos() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MainViewModel.CALL_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MainViewModel.CALL_PERMISSION_REQUEST_CODE) {
//            Ver si el usuario acepta los permisos
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Llamar al numero de la inmobiliaria
                viewModel.verificarPermisosDeLlamada();
            } else {
//                Rechazaron el permiso, mostrar un mensaje
                Toast.makeText(this, "Permiso rechazado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}