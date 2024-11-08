package com.example.ex1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ex1.DB.DatabaseHelper;
import com.example.ex1.Entities.Ordenador;

import java.util.List;

public class OrdenadorAdapter extends RecyclerView.Adapter<OrdenadorAdapter.OrdenadorViewHolder> {

    private Context context;
    private List<Ordenador> ordenadores;
    private DatabaseHelper db;
    private OnOrdenadorChangeListener listener;

    public OrdenadorAdapter(Context context, List<Ordenador> ordenadores, DatabaseHelper db, OnOrdenadorChangeListener listener) {
        this.context = context;
        this.ordenadores = ordenadores;
        this.db = db;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrdenadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ordenador, parent, false);
        return new OrdenadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdenadorViewHolder holder, int position) {
        Ordenador ordenador = ordenadores.get(position);
        holder.textNombre.setText(ordenador.getNombre());
        holder.textModelo.setText(ordenador.getModelo());
        holder.textPrecio.setText(String.valueOf(ordenador.getPrecio()));

        holder.btnEditar.setOnClickListener(v -> {
            OrdenadorDialog dialog = new OrdenadorDialog(context, db, ordenador, () -> listener.onOrdenadorChanged());
            dialog.showDialog();
        });

        holder.btnEliminar.setOnClickListener(v -> {
            int rowsDeleted = db.deleteOrdenador(ordenador.getId());
            if (rowsDeleted > 0) {
                Toast.makeText(context, "Ordenador eliminado", Toast.LENGTH_SHORT).show();
                listener.onOrdenadorChanged();  // Notifica el cambio para refrescar la lista
            } else {
                Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ordenadores.size();
    }

    // Método para actualizar la lista de datos
    public void updateData(List<Ordenador> newOrdenadores) {
        this.ordenadores = newOrdenadores;
        notifyDataSetChanged();
    }

    public static class OrdenadorViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre, textModelo, textPrecio;
        Button btnEditar, btnEliminar;

        public OrdenadorViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.textNombre);
            textModelo = itemView.findViewById(R.id.textModelo);
            textPrecio = itemView.findViewById(R.id.textPrecio);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }

    // Interfaz para notificar cambios en la lista
    public interface OnOrdenadorChangeListener {
        void onOrdenadorChanged();
    }
}
