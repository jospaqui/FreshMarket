package com.example.itm_proyectofinal.Beans;

import android.graphics.Bitmap;

public class Producto {
    private int codigo;
    private String nombre;
    private double precio;
    private String criterio_medida;
    private int stock;
    private Bitmap imagen;
    private String descripcion;
    private int codAgri;
    private int codCla;

    public Producto(){}

    public Producto(int codigo, String nombre, double precio, String criterio_medida, int stock, Bitmap imagen, String descripcion, int codAgri, int codCla) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.criterio_medida = criterio_medida;
        this.stock = stock;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.codAgri=codAgri;
        this.codCla=codCla;


    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCriterio_medida() {
        return criterio_medida;
    }

    public void setCriterio_medida(String criterio_medida) {
        this.criterio_medida = criterio_medida;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCodAgri() {
        return codAgri;
    }

    public void setCodAgri(int codAgri) {
        this.codAgri = codAgri;
    }

    public int getCodCla() {
        return codCla;
    }

    public void setCodCla(int codCla) {
        this.codCla = codCla;
    }
}
