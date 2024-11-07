package com.example.ex1;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ex1.DB.DatabaseHelper;
import com.example.ex1.Entities.Ordenador;

public class OrdenadorDialog {

    private Context context;
    private DatabaseHelper db;
    private Ordenador ordenador; // Puede ser null si es para añadir
    private OnOrdenadorSavedListener listener; // Callback para actualizar la lista en MainActivity

    public OrdenadorDialog(Context context, DatabaseHelper db, Ordenador ordenador, OnOrdenadorSavedListener listener) {
        this.context = context;
        this.db = db;
        this.ordenador = ordenador;
        this.listener = listener;
    }

    public void showDialog() {
        // Crear el cuadro de diálogo
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_edit_ordenador);
        dialog.setTitle(ordenador != null ? "Editar Ordenador" : "Añadir Ordenador");

        // Obtener referencias a los EditTexts y el botón en el layout
        EditText editTextNombre = dialog.findViewById(R.id.editTextNombre);
        EditText editTextModelo = dialog.findViewById(R.id.editTextModelo);
        EditText editTextPrecio = dialog.findViewById(R.id.editTextPrecio);
        Button btnGuardar = dialog.findViewById(R.id.btnGuardar);

        // Rellenar campos si es edición
        if (ordenador != null) {
            editTextNombre.setText(ordenador.getNombre());
            editTextModelo.setText(ordenador.getModelo());
            editTextPrecio.setText(String.valueOf(ordenador.getPrecio()));
        }

        // Configurar el botón de guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validar la entrada del usuario
                String nombre = editTextNombre.getText().toString().trim();
                String modelo = editTextModelo.getText().toString().trim();
                String precioStr = editTextPrecio.getText().toString().trim();

                if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(modelo) || TextUtils.isEmpty(precioStr)) {
                    Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                Double precio;
                try {
                    precio = Double.parseDouble(precioStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(context, "El precio debe ser un número válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Guardar en la base de datos
                if (ordenador == null) {
                    // Añadir un nuevo ordenador
                    long id = db.addOrdenador(nombre, modelo, precio);
                    if (id != -1L) {
                        Toast.makeText(context, "Ordenador añadido correctamente", Toast.LENGTH_SHORT).show();
                        listener.onOrdenadorSaved(); // Notificar que se ha añadido un ordenador
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Error al añadir el ordenador", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Editar ordenador existente
                    ordenador.setNombre(nombre);
                    ordenador.setModelo(modelo);
                    ordenador.setPrecio(precio);
                    int rowsUpdated = db.updateOrdenador(ordenador.getId(), nombre, modelo, precio);
                    if (rowsUpdated > 0) {
                        Toast.makeText(context, "Ordenador actualizado correctamente", Toast.LENGTH_SHORT).show();
                        listener.onOrdenadorSaved(); // Notificar que se ha editado el ordenador
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Error al actualizar el ordenador", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Mostrar el diálogo
        dialog.show();
    }

    // Interfaz de callback para notificar cuando un ordenador ha sido guardado
    public interface OnOrdenadorSavedListener {
        void onOrdenadorSaved();
    }
}
