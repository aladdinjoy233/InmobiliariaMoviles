package com.example.inmobiliariamoviles.ui.inmuebles;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.inmobiliariamoviles.R;
import com.example.inmobiliariamoviles.databinding.FragmentDetalleInmuebleBinding;

public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel viewModel;
    private FragmentDetalleInmuebleBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);

        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.procesarDatos(getArguments());

        viewModel.getInmueble().observe(getViewLifecycleOwner(), inmueble -> {

            Glide.with(binding.ivFotoInmueble.getContext())
                    .load(inmueble.getImagen())
                    .into(binding.ivFotoInmueble);

            binding.tvCodigo.setText(String.valueOf(inmueble.getIdInmueble()));
            binding.tvDireccion.setText(inmueble.getDireccion());
            binding.tvUso.setText(inmueble.getUso());
            binding.tvTipo.setText(inmueble.getTipo());

            binding.tvAmbientes.setText(String.valueOf(inmueble.getAmbientes()));
            binding.tvPrecio.setText(String.valueOf(inmueble.getPrecio()));
            binding.cbDisponible.setChecked(inmueble.isEstado());
        });

        binding.cbDisponible.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.setEstado(isChecked);
        });

        return root;
    }

}