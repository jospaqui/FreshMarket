package com.example.itm_proyectofinal.Beans;

public class VentaDetalle {
    private int id_venta;
    private int id_prod;
    private int cantidad;
    private double precio_cant;


    public VentaDetalle(){}

    public VentaDetalle(int id_venta, int id_prod, int cantidad, double precio_cant) {
        this.id_venta = id_venta;
        this.id_prod = id_prod;
        this.cantidad = cantidad;
        this.precio_cant = precio_cant;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public int getId_prod() {
        return id_prod;
    }

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_cant() {
        return precio_cant;
    }

    public void setPrecio_cant(double precio_cant) {
        this.precio_cant = precio_cant;
    }
}
