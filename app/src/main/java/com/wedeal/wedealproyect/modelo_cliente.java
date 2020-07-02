package com.wedeal.wedealproyect;

public class modelo_cliente {
    private String nombre;
    private String tipo_cliente;
    private String telefono;
    private String nm_compras;

    private int imgs;

    public modelo_cliente(){

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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNm_Compras() {
        return nm_compras;
    }

    public void setNm_Compras(String nm_compras) {
        this.nm_compras = nm_compras;
    }

    public int getImgs() {
        return imgs;
    }

    public void setImgs(int imgs) {
        this.imgs = imgs;
    }

    public modelo_cliente(String nombre, String cargo, String telefono, String salario, int imgs) {
        this.nombre = nombre;
        this.tipo_cliente = tipo_cliente;
        this.telefono = telefono;
        this.nm_compras = nm_compras;
        this.imgs = imgs;
    }
}
