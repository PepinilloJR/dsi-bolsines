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
}