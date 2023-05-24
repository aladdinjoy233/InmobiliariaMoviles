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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        holder.binding.tvCodigoPago.setText(String.valueOf(pago.getId_Pago()));
        holder.binding.tvNumeroPago.setText(String.valueOf(pago.getNumero()));
        holder.binding.tvCodigoContrato.setText(String.valueOf(pago.getContrato().getId_Contrato()));

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        String formattedPrice = numberFormat.format(pago.getImporte());
        holder.binding.tvImporte.setText(formattedPrice);

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date fecha = inputFormat.parse(pago.getFecha());

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            holder.binding.tvFecha.setText(outputFormat.format(fecha));
        } catch (ParseException e) {
            holder.binding.tvFecha.setText(pago.getFecha());
        }

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
