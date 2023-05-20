package com.example.inmobiliariamoviles.ui.contratos;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inmobiliariamoviles.databinding.CardContratoBinding;
import com.example.inmobiliariamoviles.databinding.CardPagoBinding;
import com.example.inmobiliariamoviles.models.Inmueble;
import com.example.inmobiliariamoviles.models.Pago;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.ViewHolder> {

    private ArrayList<Pago> pagos;
    private LayoutInflater inflater;

    public PagoAdapter(ArrayList<Pago> pagos, LayoutInflater inflater) {
        this.pagos = pagos;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardPagoBinding binding = CardPagoBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pago pago = pagos.get(position);

        holder.binding.tvCodigoPago.setText(String.valueOf(pago.getIdPago()));
        holder.binding.tvNumeroPago.setText(String.valueOf(pago.getNumero()));
        holder.binding.tvCodigoContrato.setText(String.valueOf(pago.getContrato().getIdContrato()));

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        String formattedPrice = numberFormat.format(pago.getImporte());
        holder.binding.tvImporte.setText(formattedPrice);

        holder.binding.tvFecha.setText(pago.getFechaDePago());
    }

    @Override
    public int getItemCount() { return pagos.size(); };

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardPagoBinding binding;

        public ViewHolder(@NonNull CardPagoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
