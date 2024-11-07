package com.example.ex1.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ordenadores.db";
    private static final int DATABASE_VERSION = 1;

    // Tabla ordenadores
    public static final String TABLE_ORDENADORES = "ordenadores";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_MODELO = "modelo";
    public static final String COLUMN_PRECIO = "precio";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_ORDENADORES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT, " +
                COLUMN_MODELO + " TEXT, " +
                COLUMN_PRECIO + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDENADORES);
        onCreate(db);
    }

    // Método para agregar un ordenador
    public long addOrdenador(String nombre, String modelo, double precio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_MODELO, modelo);
        values.put(COLUMN_PRECIO, precio);
        return db.insert(TABLE_ORDENADORES, null, values);
    }

    // Método para obtener todos los ordenadores
    public Cursor getAllOrdenadores() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ORDENADORES, null);
    }

    // Método para actualizar un ordenador
    public int updateOrdenador(int id, String nombre, String modelo, double precio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_MODELO, modelo);
        values.put(COLUMN_PRECIO, precio);
        return db.update(TABLE_ORDENADORES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Método para eliminar un ordenador
    public int deleteOrdenador(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ORDENADORES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
}

