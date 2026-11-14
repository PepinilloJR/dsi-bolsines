package com.bolsines.demo.Domain;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @Column(nullable = false, unique = true)
    private String nombreUsuario;

    private String password;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;


    public Empleado getUsuario() {
        return this.empleado;
    }
}

// cuidado