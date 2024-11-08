package com.example.ex1.Entities;

public class Ordenador {
    private int id;
    private String nombre;
    private String modelo;
    private double precio;


    public Ordenador(int id, String nombre, String modelo, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.modelo = modelo;
        this.precio = precio;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
