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

import java.text.NumberFormat;
import java.util.Locale;

public class DetalleContratoFragment extends Fragment {

    private DetalleContratoViewModel viewModel;
    private FragmentDetalleContratoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(DetalleContratoViewModel.class);

        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.procesarDatos(getArguments());

        viewModel.getContrato().observe(getViewLifecycleOwner(), contrato -> {
            binding.tvCodigo.setText(String.valueOf(contrato.getIdContrato()));
            binding.tvFechaInicio.setText(contrato.getFechaInicio());
            binding.tvFechaFin.setText(contrato.getFechaFin());

            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
            String formattedPrice = numberFormat.format(contrato.getMontoAlquiler());
            binding.tvMonto.setText(formattedPrice);

            binding.tvInquilino.setText(contrato.getInquilino().getNombre() + " " + contrato.getInquilino().getApellido());
            binding.tvInmueble.setText("Inmueble en " + contrato.getInmueble().getDireccion());
        });

        binding.btPagos.setOnClickListener(v -> {
            viewModel.verPagos(v);
        });

        return root;
    }

}