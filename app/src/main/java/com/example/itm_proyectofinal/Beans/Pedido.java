package com.example.itm_proyectofinal.Beans;

public class Pedido {
    private  int codPedi;
    private int codProd;
    private int codAgri;
    private int codClas;
    private int cantidad;
    private double cant_precio;

    public Pedido(){}

    public Pedido(int codPedi, int codProd, int codAgri, int codClas, int cantidad, double cant_precio) {
        this.codPedi=codPedi;
        this.codProd = codProd;
        this.codAgri = codAgri;
        this.codClas = codClas;
        this.cantidad = cantidad;
        this.cant_precio = cant_precio;
    }

    public int getCodPedi() {
        return codPedi;
    }

    public void setCodPedi(int codPedi) {
        this.codPedi = codPedi;
    }

    public int getCodProd() {
        return codProd;
    }

    public void setCodProd(int codProd) {
        this.codProd = codProd;
    }

    public int getCodAgri() {
        return codAgri;
    }

    public void setCodAgri(int codAgri) {
        this.codAgri = codAgri;
    }

    public int getCodClas() {
        return codClas;
    }

    public void setCodClas(int codClas) {
        this.codClas = codClas;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getCant_precio() {
        return cant_precio;
    }

    public void setCant_precio(double cant_precio) {
        this.cant_precio = cant_precio;
    }
}
