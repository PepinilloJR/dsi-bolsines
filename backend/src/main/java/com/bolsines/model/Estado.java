package com.bolsines.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estados")
@Data
@NoArgsConstructor
public class Estado {

    @Id
    @Column(nullable = false, unique = true)
    private String nombre;

    private String ambito;

    private String descripcion;

    public Estado(String ambito, String descripcion, String nombre) {
        this.ambito = ambito;
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    public boolean esEnviado() {
        return "Enviado".equals(this.nombre);
    }

    // * getter y setters *
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}