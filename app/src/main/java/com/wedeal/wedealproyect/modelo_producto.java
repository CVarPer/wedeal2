package com.wedeal.wedealproyect;

import android.graphics.Bitmap;
import android.net.Uri;

public class modelo_producto {
    private String codigo;
    private String nombre;
    private String precio;
    private String stock;
    private String fecha;
    private Uri fotoProd;

    public modelo_producto(String codigo, String nombre, String precio, String stock, String fecha, Uri fotoProd) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.fecha = fecha;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Uri getFotoProd() {
        return fotoProd;
    }

    public void setFotoProd(Uri fotoProd) {
        this.fotoProd = fotoProd;
    }
}