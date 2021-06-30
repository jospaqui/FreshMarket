package com.example.itm_proyectofinal.Beans;

public class Comentario {
    private int idComent;
    private String contenido;
    private int idClas;
    private int idProd;
    private String fecha;


    public Comentario(){}

    public Comentario(int idComent, String contenido, int idClas, int idProd, String fecha) {
        this.idComent = idComent;
        this.contenido = contenido;
        this.idClas = idClas;
        this.idProd = idProd;
        this.fecha = fecha;
    }

    public int getIdComent() {
        return idComent;
    }

    public void setIdComent(int idComent) {
        this.idComent = idComent;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public int getIdClas() {
        return idClas;
    }

    public void setIdClas(int idClas) {
        this.idClas = idClas;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
