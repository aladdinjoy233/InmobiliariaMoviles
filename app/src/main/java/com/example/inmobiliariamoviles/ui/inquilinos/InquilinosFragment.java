package com.example.inmobiliariamoviles.ui.inquilinos;

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
import com.example.inmobiliariamoviles.databinding.FragmentInquilinosBinding;
import com.example.inmobiliariamoviles.ui.inmuebles.InmuebleAdapter;

public class InquilinosFragment extends Fragment {

    private FragmentInquilinosBinding binding;
    InquilinosViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(InquilinosViewModel.class);

        binding = FragmentInquilinosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.getInmuebles().observe(getViewLifecycleOwner(), inmuebles -> {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
            binding.rvInquilinos.setLayoutManager(gridLayoutManager);
            InquilinoAdapter adapter = new InquilinoAdapter(inmuebles, inflater);
            binding.rvInquilinos.setAdapter(adapter);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}