package com.example.inmobiliariamoviles.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inmobiliariamoviles.R;
import com.example.inmobiliariamoviles.databinding.CardContratoBinding;
import com.example.inmobiliariamoviles.models.Contrato;
import com.example.inmobiliariamoviles.models.Inmueble;

import java.util.ArrayList;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ViewHolder>{

    private ArrayList<Contrato> contratos;
    private ArrayList<Inmueble> inmuebles;
    private LayoutInflater inflater;

    public ContratoAdapter(ArrayList<Inmueble> inmuebles, LayoutInflater inflater) {
        this.inmuebles = inmuebles;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardContratoBinding binding = CardContratoBinding.inflate(inflater, parent, false);
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
        CardContratoBinding binding;

        public ViewHolder(@NonNull CardContratoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.btVer.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable("inmueble", inmuebles.get(getAdapterPosition()));
                Navigation.findNavController(v).navigate(R.id.detalle_contrato_fragment, bundle);
            });
        }
    }
}
