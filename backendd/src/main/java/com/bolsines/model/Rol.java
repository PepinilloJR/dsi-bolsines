package com.bolsines.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol {

    @Id
    @Column(nullable = false, unique = true)
    private String nombre;

    private String descripcion;

    public String getDescripcion() {
        return this.descripcion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Boolean esGCM() {
        return "GCM".equalsIgnoreCase(this.nombre);
    }
}