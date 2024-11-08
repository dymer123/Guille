package com.example.ex1

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ex1.DB.DatabaseHelper
import com.example.ex1.Entities.Ordenador

class MainActivity : AppCompatActivity() {
    private var db: DatabaseHelper? = null
    private var adapter: OrdenadorAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        db = DatabaseHelper(this)

        // Inicializar RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inicializar el adaptador y asignarlo al RecyclerView
        adapter = OrdenadorAdapter(this, listOf(), db) { loadData() }
        recyclerView.adapter = adapter

        // Cargar datos inicialmente
        loadData()

        // Botón para añadir un nuevo ordenador
        findViewById<View>(R.id.btnAddOrdenador).setOnClickListener {
            val dialog = OrdenadorDialog(this, db, null) { loadData() }
            dialog.showDialog()
        }
    }

    private fun loadData() {
        val ordenadores = db?.allOrdenadores ?: listOf()
        adapter?.updateData(ordenadores)
    }
}
