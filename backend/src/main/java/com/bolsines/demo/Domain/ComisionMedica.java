package com.bolsines.demo.Domain;

import jakarta.persistence.*;

@Entity
@Table(name = "comisiones_medicas")
public class ComisionMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer codigo;

    private String direccion;

    private String mail;

    private String nombre;

    private String telefono;


    public String getDatos() {
        return this.codigo + " - " + this.direccion + " - " + this.mail + " - " + this.nombre + " - " + this.telefono;
    }

    public Integer getCodigo() {
        return this.codigo;
    }
}