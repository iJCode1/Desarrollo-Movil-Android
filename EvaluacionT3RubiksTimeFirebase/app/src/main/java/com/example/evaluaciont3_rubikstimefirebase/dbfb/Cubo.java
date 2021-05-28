package com.example.evaluaciont3_rubikstimefirebase.dbfb;

import java.io.Serializable;
import java.util.Date;

public class Cubo {
    private String Uid;
    private int id;
    private String Nombre;
    private String Descripcion;
    private String Fecha;
    private String Categoria;
    private String Status;
    private float Tiempo;
    private Serializable Image;
    private String Path;

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public Float getTiempo() {
        return Tiempo;
    }

    public void setTiempo(Float tiempo) {
        this.Tiempo = tiempo;
    }

    public Serializable getImage() {
        return Image;
    }

    public void setImage(Serializable image) {
        this.Image = image;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        this.Path = path;
    }

    public String toString(){
        return (Nombre + " " + Tiempo);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
