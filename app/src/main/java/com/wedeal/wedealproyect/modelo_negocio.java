package com.wedeal.wedealproyect;

public class modelo_negocio {

    private String nombre;
    private String direccion;
    private String telefono;
    private int imgs;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion){
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono){
        this.telefono = telefono;
    }

    public int getImgs(){
        return imgs;
    }

    public void setImgs(int imgs){
        this.imgs = imgs;
    }

    public modelo_negocio(String nombre, String direccion, String telefono,  int imgs){
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.imgs = imgs;

    }

}