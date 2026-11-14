package com.bolsines.demo.Domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

@Entity
@Table(name = "sesiones")
@AllArgsConstructor
@NoArgsConstructor

public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime fechaHoraFin;

    private LocalDateTime fechaHoraInicio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


    public Usuario getUsuarioLogueado() {
        this.usuario.getUsuario().getCM().getDatos();
        return usuario;
    }
}