package com.bolsines.demo.Domain;

public class Rol {
    String descripcion;
    String nombre;

    public Boolean esGCM() {
        return this.nombre.equals("GCM");
    };

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return this.nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Rol newRol(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        return this;
    }

    public void delete() {
        this.delete();
    }
    // Esto se fixea despues
}
