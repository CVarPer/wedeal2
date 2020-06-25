package com.wedeal.wedealproyect;

import android.graphics.Bitmap;

public class modelo_producto {
    private String codigo;
    private String nombre;
    private String precio;
    private String stock;
    private Bitmap fotoProd;

    public modelo_producto(String codigo, String nombre, String precio, String stock, Bitmap fotoProd) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.fotoProd = fotoProd;
    }

    public modelo_producto(){

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public Bitmap getFotoProd() {
        return fotoProd;
    }

    public void setFotoProd(Bitmap fotoProd) {
        this.fotoProd = fotoProd;
    }
}