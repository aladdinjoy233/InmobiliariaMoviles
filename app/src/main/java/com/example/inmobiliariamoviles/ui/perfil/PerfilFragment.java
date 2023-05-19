package com.example.inmobiliariamoviles.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariamoviles.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    PerfilViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.getPropietario().observe(getViewLifecycleOwner(), propietario -> {
            binding.etId.setText(String.valueOf(propietario.getId()));
            binding.etDni.setText(String.valueOf(propietario.getDni()));
            binding.etNombre.setText(propietario.getNombre());
            binding.etApellido.setText(propietario.getApellido());
            binding.etEmail.setText(propietario.getEmail());
            binding.etPass.setText(propietario.getContraseña());
            binding.etTelefono.setText(propietario.getTelefono());
        });

        viewModel.getIsEdit().observe(getViewLifecycleOwner(), isEdit -> {
            binding.etDni.setEnabled(isEdit);
            binding.etNombre.setEnabled(isEdit);
            binding.etApellido.setEnabled(isEdit);
            binding.etEmail.setEnabled(isEdit);
            binding.etPass.setEnabled(isEdit);
            binding.etTelefono.setEnabled(isEdit);

            binding.btEditar.setVisibility(isEdit ? View.GONE : View.VISIBLE);
            binding.btGuardar.setVisibility(isEdit ? View.VISIBLE : View.GONE);
        });

        binding.btEditar.setOnClickListener(v -> {
            viewModel.cambiarEstadoEdit();
        });

        binding.btGuardar.setOnClickListener(v -> {
            viewModel.guardarPerfil(
                    Long.parseLong(binding.etDni.getText().toString()),
                    binding.etNombre.getText().toString(),
                    binding.etApellido.getText().toString(),
                    binding.etEmail.getText().toString(),
                    binding.etPass.getText().toString(),
                    binding.etTelefono.getText().toString()
            );
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}