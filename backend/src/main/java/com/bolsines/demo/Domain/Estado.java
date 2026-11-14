package com.bolsines.demo.Domain;

import jakarta.persistence.*;

@Entity
@Table(name = "estados")
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
}