package com.example.inmobiliariamoviles.ui.inmuebles;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.inmobiliariamoviles.R;
import com.example.inmobiliariamoviles.databinding.CardInmuebleBinding;
import com.example.inmobiliariamoviles.models.Inmueble;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolder> {

    private ArrayList<Inmueble> inmuebles;
    private LayoutInflater inflater;

    public InmuebleAdapter(ArrayList<Inmueble> inmuebles, LayoutInflater inflater) {
        this.inmuebles = inmuebles;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardInmuebleBinding binding = CardInmuebleBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Inmueble inmueble = inmuebles.get(position);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        String formattedNumber = numberFormat.format(inmueble.getPrecio());

        holder.binding.tvDireccionInmueble.setText(inmueble.getDireccion());
        holder.binding.tvPrecioInmueble.setText(formattedNumber);

        Glide.with(holder.binding.ivFotoInmueble.getContext())
                .load(inmueble.getImagen())
                .into(holder.binding.ivFotoInmueble);
    }

    @Override
    public int getItemCount() { return inmuebles.size(); };

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardInmuebleBinding binding;

        public ViewHolder(@NonNull CardInmuebleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable("inmueble", inmuebles.get(getAdapterPosition()));
                Navigation.findNavController(v).navigate(R.id.detalle_inmueble_fragment, bundle);
            });
        }
    }
}
