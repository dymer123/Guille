package com.example.ex1.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ex1.Entities.Ordenador;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ordenadores.db";
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla y columnas
    private static final String TABLE_ORDENADORES = "ordenadores";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_MODELO = "modelo";
    private static final String COLUMN_PRECIO = "precio";

    // Consulta de creación de la tabla
    private static final String CREATE_TABLE_ORDENADORES =
            "CREATE TABLE " + TABLE_ORDENADORES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOMBRE + " TEXT, " +
                    COLUMN_MODELO + " TEXT, " +
                    COLUMN_PRECIO + " REAL)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ORDENADORES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDENADORES);
        onCreate(db);
    }

    // Método para añadir un ordenador a la base de datos
    public long addOrdenador(String nombre, String modelo, double precio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_MODELO, modelo);
        values.put(COLUMN_PRECIO, precio);

        long id = db.insert(TABLE_ORDENADORES, null, values);
        db.close();
        return id;
    }

    // Método para actualizar un ordenador existente en la base de datos
    public int updateOrdenador(int id, String nombre, String modelo, double precio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_MODELO, modelo);
        values.put(COLUMN_PRECIO, precio);

        int rowsUpdated = db.update(TABLE_ORDENADORES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsUpdated;
    }

    // Método para eliminar un ordenador de la base de datos
    public int deleteOrdenador(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_ORDENADORES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted;
    }

    // Método para obtener todos los ordenadores en forma de lista
    public List<Ordenador> getAllOrdenadores() {
        List<Ordenador> ordenadores = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ORDENADORES, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE));
                String modelo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MODELO));
                double precio = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRECIO));

                Ordenador ordenador = new Ordenador(id, nombre, modelo, precio);
                ordenadores.add(ordenador);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return ordenadores;
    }
}
