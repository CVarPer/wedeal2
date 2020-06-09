package com.wedeal.wedealproyect;

public class ObjetoListView {

    private String nombre_empl, telefono, salario;

    public ObjetoListView(String nombre_empl, String telefono, String salario) {
        this.nombre_empl = nombre_empl;
        this.telefono = telefono;
        this.salario = salario;
    }

    public String getNombre_empl() {
        return nombre_empl;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getSalario() {
        return salario;
    }

}
