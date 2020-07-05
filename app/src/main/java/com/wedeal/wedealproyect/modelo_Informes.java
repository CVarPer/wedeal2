package com.wedeal.wedealproyect;

public class modelo_Informes {

    private String mes;
    private String pagos;
    private String salario;
    private String gastosTotal;
    private String promVentas;
    private String GanBrut;
    private String GanTotal;

    public modelo_Informes(String mes, String pagos, String salario, String gastosTotal, String promVentas, String ganBrut, String ganTotal) {
        this.mes = mes;
        this.pagos = pagos;
        this.salario = salario;
        this.gastosTotal = gastosTotal;
        this.promVentas = promVentas;
        this.GanBrut = ganBrut;
        this.GanTotal = ganTotal;
    }

    public modelo_Informes() {
    }


    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getPagos() {
        return pagos;
    }

    public void setPagos(String pagos) {
        this.pagos = pagos;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    public String getGastosTotal() {
        return gastosTotal;
    }

    public void setGastosTotal(String gastosTotal) {
        this.gastosTotal = gastosTotal;
    }

    public String getPromVentas() {
        return promVentas;
    }

    public void setPromVentas(String promVentas) {
        this.promVentas = promVentas;
    }

    public String getGanBrut() {
        return GanBrut;
    }

    public void setGanBrut(String ganBrut) {
        GanBrut = ganBrut;
    }

    public String getGanTotal() {
        return GanTotal;
    }

    public void setGanTotal(String ganTotal) {
        GanTotal = ganTotal;
    }
}
