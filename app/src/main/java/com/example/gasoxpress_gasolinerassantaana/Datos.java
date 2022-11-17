package com.example.gasoxpress_gasolinerassantaana;

public class Datos {
    //Propiedades
    private int id;
    private String descripcion;
    private String gasolinera;
    private String latitud;
    private String longitud;
    private String foto;

    public Datos(int id, String descripcion, String gasolinera, String latitud, String longitud, String foto) {
        this.id = id;
        this.descripcion = descripcion;
        this.gasolinera = gasolinera;
        this.latitud = latitud;
        this.longitud = longitud;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGasolinera() {
        return gasolinera;
    }

    public void setGasolinera(String gasolinera) {
        this.gasolinera = gasolinera;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
