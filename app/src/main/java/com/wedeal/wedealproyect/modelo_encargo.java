package com.wedeal.wedealproyect;

public class modelo_encargo {
    private String estado;
    private String fecha;
    private String nombre;
    private String valor_compra;

    public modelo_encargo(String estado, String fecha, String valor_compra, String nombre) {
        this.estado = estado;
        this.nombre = nombre;
        this.valor_compra = valor_compra;
        this.fecha = fecha;
    }

    public modelo_encargo(){

    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getValor_Compra() {
        return valor_compra;
    }

    public void setValor_Compra(String valor_compra) {
        this.valor_compra = valor_compra;
    }

}
