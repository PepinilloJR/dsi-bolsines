package com.bolsines.demo.Domain;

import java.time.LocalDateTime;

public class CambioEstado {
    LocalDateTime fechaHoraFin;
    LocalDateTime fechaHoraInicio;
    Estado estado;

    boolean sosActual() {
        return fechaHoraFin == null;
    }

    boolean sosEnviado() {
        return estado.esEnviado();
    }
}

// Todo listo
