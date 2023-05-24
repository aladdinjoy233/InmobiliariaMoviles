package com.example.inmobiliariamoviles.ui.inquilinos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inmobiliariamoviles.R;
import com.example.inmobiliariamoviles.databinding.CardInquilinoBinding;
import com.example.inmobiliariamoviles.models.Inmueble;
import com.example.inmobiliariamoviles.models.Inquilino;

import java.util.ArrayList;

public class InquilinoAdapter extends RecyclerView.Adapter<InquilinoAdapter.ViewHolder>{

    private ArrayList<Inquilino> inquilinos;
    private ArrayList<Inmueble> inmuebles;
    private LayoutInflater inflater;

    public InquilinoAdapter(ArrayList<Inmueble> inmuebles, LayoutInflater inflater) {
        this.inmuebles = inmuebles;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardInquilinoBinding binding = CardInquilinoBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Inmueble inmueble = inmuebles.get(position);

        holder.binding.tvDireccionInmueble.setText(inmueble.getDireccion());

        Glide.with(holder.binding.ivFotoInmueble.getContext())
                .load(inmueble.getImagen())
                .into(holder.binding.ivFotoInmueble);
    }

    @Override
    public int getItemCount() { return inmuebles.size(); };

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardInquilinoBinding binding;

        public ViewHolder(@NonNull CardInquilinoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.btVer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("inmueble", inmuebles.get(getAdapterPosition()));
                    Navigation.findNavController(v).navigate(R.id.detalle_inquilino_fragment, bundle);
                }
            });
        }
    }
}
