package com.wedeal.wedealproyect;

public class modelo_solicitud_cliente {
    private String telefono;
    private String nombre;
    private String tipo_cliente;
    private String valor_compra;
    private int fotoProd;

    public modelo_solicitud_cliente(String telefono, String tipo_cliente, String valor_compra, String nombre, int fotoProd) {
        this.telefono = telefono;
        this.nombre = nombre;
        this.valor_compra = valor_compra;
        this.tipo_cliente = tipo_cliente;
        this.fotoProd = fotoProd;
    }

    public modelo_solicitud_cliente(){

    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo_Cliente() {
        return tipo_cliente;
    }

    public void setTipo_Cliente(String tipo_cliente) {
        this.tipo_cliente = tipo_cliente;
    }

    public String getValor_Compra() {
        return valor_compra;
    }

    public void setValor_Compra(String valor_compra) {
        this.valor_compra = valor_compra;
    }

    public int getFotoProd() {
        return fotoProd;
    }

    public void setFotoProd(int fotoProd) {
        this.fotoProd = fotoProd;
    }
}
