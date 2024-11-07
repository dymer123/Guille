package com.example.ex1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ex1.Entities.Ordenador;

import java.util.List;

public class OrdenadorAdapter extends RecyclerView.Adapter<OrdenadorAdapter.OrdenadorViewHolder> {

    private List<Ordenador> ordenadorList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(Ordenador ordenador);
        void onDeleteClick(Ordenador ordenador);
    }

    public OrdenadorAdapter(List<Ordenador> ordenadorList, OnItemClickListener listener) {
        this.ordenadorList = ordenadorList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrdenadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ordenador, parent, false);
        return new OrdenadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdenadorViewHolder holder, int position) {
        Ordenador ordenador = ordenadorList.get(position);
        holder.textViewNombre.setText(ordenador.getNombre());
        holder.textViewModelo.setText(ordenador.getModelo());
        holder.textViewPrecio.setText(String.valueOf(ordenador.getPrecio()));

        holder.itemView.findViewById(R.id.btnEdit).setOnClickListener(v -> listener.onEditClick(ordenador));
        holder.itemView.findViewById(R.id.btnDelete).setOnClickListener(v -> listener.onDeleteClick(ordenador));
    }

    @Override
    public int getItemCount() {
        return ordenadorList.size();
    }

    public static class OrdenadorViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombre, textViewModelo, textViewPrecio;

        public OrdenadorViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewModelo = itemView.findViewById(R.id.textViewModelo);
            textViewPrecio = itemView.findViewById(R.id.textViewPrecio);
        }
    }
}
