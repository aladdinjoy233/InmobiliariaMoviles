package com.example.inmobiliariamoviles.ui.contratos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliariamoviles.R;
import com.example.inmobiliariamoviles.databinding.FragmentDetalleContratoBinding;

public class DetalleContratoFragment extends Fragment {

    private DetalleContratoViewModel viewModel;
    private FragmentDetalleContratoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(DetalleContratoViewModel.class);

        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.procesarDatos(getArguments());

        return root;
    }

}