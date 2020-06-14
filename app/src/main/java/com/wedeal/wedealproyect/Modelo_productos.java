package com.wedeal.wedealproyect;

public class Modelo_productos {
    private String codigo;
    private String nombre;
    private String precio;
    private String stock;
    private int fotoProd;

    public Modelo_productos(String codigo, String nombre, String precio, String stock, int fotoProd) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.fotoProd = fotoProd;
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

    public int getFotoProd() {
        return fotoProd;
    }

    public void setFotoProd(int fotoProd) {
        this.fotoProd = fotoProd;
    }
}
