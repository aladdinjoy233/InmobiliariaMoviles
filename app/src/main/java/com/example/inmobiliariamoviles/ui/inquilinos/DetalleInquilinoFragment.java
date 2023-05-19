package com.example.inmobiliariamoviles.ui.inquilinos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliariamoviles.databinding.FragmentDetalleInquilinoBinding;

public class DetalleInquilinoFragment extends Fragment {

    private DetalleInquilinoViewModel viewModel;
    private FragmentDetalleInquilinoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);

        binding = FragmentDetalleInquilinoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.procesarDatos(getArguments());

        viewModel.getInquilino().observe(getViewLifecycleOwner(), inquilino -> {
           binding.tvCodigo.setText(String.valueOf(inquilino.getIdInquilino()));
           binding.tvNombre.setText(inquilino.getNombre());
           binding.tvApellido.setText(inquilino.getApellido());
           binding.tvDni.setText(String.valueOf(inquilino.getDNI()));
           binding.tvEmail.setText(inquilino.getEmail());
           binding.tvTelefono.setText(inquilino.getTelefono());
           binding.tvGarante.setText(inquilino.getNombreGarante());
           binding.tvTelefonoGarante.setText(inquilino.getTelefonoGarante());
        });

        return root;
    }

}