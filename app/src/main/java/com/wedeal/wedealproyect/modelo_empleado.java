package com.wedeal.wedealproyect;

public class modelo_empleado {
    private String nombre;
    private String cargo;
    private String telefono;
    private String salario;

    private int imgs;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    public int getImgs() {
        return imgs;
    }

    public void setImgs(int imgs) {
        this.imgs = imgs;
    }

    public modelo_empleado(String nombre, String cargo, String telefono, String salario, int imgs) {
        this.nombre = nombre;
        this.cargo = cargo;
        this.telefono = telefono;
        this.salario = salario;
        this.imgs = imgs;
    }
}
