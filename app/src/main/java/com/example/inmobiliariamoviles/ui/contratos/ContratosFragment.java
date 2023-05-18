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
import com.example.inmobiliariamoviles.databinding.FragmentContratosBinding;

public class ContratosFragment extends Fragment {

    private FragmentContratosBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ContratosViewModel viewModel = new ViewModelProvider(this).get(ContratosViewModel.class);

        binding = FragmentContratosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}