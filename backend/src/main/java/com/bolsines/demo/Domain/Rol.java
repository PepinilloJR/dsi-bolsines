package com.bolsines.demo.Domain;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    private String descripcion;

    private String nombre;

    public Boolean esGCM() {
        return this.nombre.equals("GCM");
    }

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
}