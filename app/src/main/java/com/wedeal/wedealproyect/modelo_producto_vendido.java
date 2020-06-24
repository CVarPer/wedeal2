package com.wedeal.wedealproyect;

public class modelo_producto_vendido {
    private String codigo;
    private String nombre;
    private String precio;
    private String stock;
    private int fotoProd;

    public modelo_producto_vendido(String codigo, String nombre, String precio, String stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public modelo_producto_vendido(){

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


}
