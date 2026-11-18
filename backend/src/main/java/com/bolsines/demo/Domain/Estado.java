package com.bolsines.demo.Domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estados")
@NoArgsConstructor
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ambito;

    private String descripcion;

    private String nombre;

    public boolean esEnviado() {
        return this.nombre.equals("Enviado");
    }

    public Estado(String ambito, String descripcion, String nombre) {
    this.ambito = ambito;
    this.descripcion = descripcion;
    this.nombre = nombre;
}
}