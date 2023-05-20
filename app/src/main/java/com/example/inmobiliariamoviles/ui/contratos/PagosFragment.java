package com.example.inmobiliariamoviles.ui.contratos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliariamoviles.R;
import com.example.inmobiliariamoviles.databinding.FragmentPagosBinding;

public class PagosFragment extends Fragment {

    private PagosViewModel viewModel;
    private FragmentPagosBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(PagosViewModel.class);

        binding = FragmentPagosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.procesarDatos(getArguments());

        viewModel.getPagos().observe(getViewLifecycleOwner(), pagos -> {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
            binding.rvPagos.setLayoutManager(gridLayoutManager);
            PagoAdapter adapter = new PagoAdapter(pagos, inflater);
            binding.rvPagos.setAdapter(adapter);
        });

        return root;
    }

}