package com.bolsines.demo.Domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
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

    public Usuario(
            String nombreUsuario,
            String password,
            Empleado empleado) {

        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.empleado = empleado;
    }

    
}

// cuidado