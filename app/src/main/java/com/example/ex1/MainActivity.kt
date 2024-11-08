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
    private var ordenadores: List<Ordenador>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        db = DatabaseHelper(this)

        // Inicializar RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Cargar datos y configurar adaptador
        loadData()
        adapter = OrdenadorAdapter(
            this, ordenadores, db
        ) { this.loadData() }
        recyclerView.adapter = adapter

        // Botón para añadir un nuevo ordenador
        findViewById<View>(R.id.btnAddOrdenador).setOnClickListener { v: View? ->
            val dialog = OrdenadorDialog(
                this, db, null
            ) { this.loadData() }
            dialog.showDialog()
        }
    }

    private fun loadData() {
        ordenadores = db!!.allOrdenadores
        if (adapter != null) {
            adapter!!.notifyDataSetChanged()
        }
    }
}