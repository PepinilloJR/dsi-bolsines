package com.bolsines.demo.Domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "cambios_estado")
public class CambioEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime fechaHoraFin;

    private LocalDateTime fechaHoraInicio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_id")
    private Estado estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bolsin_id")
    private Bolsin bolsin;


    boolean sosActual() {
        return fechaHoraFin == null;
    }

    boolean sosEnviado() {
        return estado.esEnviado();
    }
}