package com.bolsines.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Rol {

    @Id
    @Column(nullable = false, unique = true)
    private String nombre;

    private String descripcion;

    public boolean esGCM() {
        return "GCM".equalsIgnoreCase(this.nombre);
    }

    // Getters y Setters estándar

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}