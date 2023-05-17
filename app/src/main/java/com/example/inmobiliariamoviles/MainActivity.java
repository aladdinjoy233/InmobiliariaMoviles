package com.example.inmobiliariamoviles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

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
}