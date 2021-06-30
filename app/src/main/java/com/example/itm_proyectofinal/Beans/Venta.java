package com.example.itm_proyectofinal.Beans;

import java.util.Date;

public class Venta {
    private int id_venta;
    private int id_agri;
    private int id_clas;
    private double delivery;
    private double monto;
    private String depart;
    private String provincia;
    private String direccion;
    private String fecha;


    public Venta(){}

    public Venta(int id_venta, int id_agri, int id_clas, double delivery, double monto, String depart, String provincia, String direccion, String fecha) {
        this.id_venta = id_venta;
        this.id_agri = id_agri;
        this.id_clas = id_clas;
        this.delivery = delivery;
        this.monto = monto;
        this.depart = depart;
        this.provincia = provincia;
        this.direccion = direccion;
        this.fecha = fecha;
    }


    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public int getId_agri() {
        return id_agri;
    }

    public void setId_agri(int id_agri) {
        this.id_agri = id_agri;
    }

    public int getId_clas() {
        return id_clas;
    }

    public void setId_clas(int id_clas) {
        this.id_clas = id_clas;
    }

    public double getDelivery() {
        return delivery;
    }

    public void setDelivery(double delivery) {
        this.delivery = delivery;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
