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
import com.example.inmobiliariamoviles.databinding.FragmentContratosBinding;
import com.example.inmobiliariamoviles.ui.inmuebles.InmuebleAdapter;

public class ContratosFragment extends Fragment {

    private FragmentContratosBinding binding;
    ContratosViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ContratosViewModel.class);

        binding = FragmentContratosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.getInmuebles().observe(getViewLifecycleOwner(), inmuebles -> {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
            binding.rvContratos.setLayoutManager(gridLayoutManager);
            ContratoAdapter adapter = new ContratoAdapter(inmuebles, inflater);
            binding.rvContratos.setAdapter(adapter);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}