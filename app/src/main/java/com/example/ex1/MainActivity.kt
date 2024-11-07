package com.example.ex1

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ex1.DB.DatabaseHelper

class MainActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        db = DatabaseHelper(this)

        // Configuración de insets para el diseño edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar el botón para abrir el diálogo de añadir ordenador
        findViewById<Button>(R.id.btnAddOrdenador).setOnClickListener {
            // Abrir el diálogo en modo añadir
            OrdenadorDialog(this, db, null, object : OrdenadorDialog.OnOrdenadorSavedListener {
                override fun onOrdenadorSaved() {
                    // Aquí puedes actualizar la lista o realizar acciones adicionales después de guardar
                }
            }).showDialog()
        }
    }
}
