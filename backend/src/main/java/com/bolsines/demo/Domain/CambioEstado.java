package com.bolsines.demo.Domain;

import java.time.LocalDateTime;

public class CambioEstado {
    LocalDateTime fechaHoraFin;
    LocalDateTime fechaHoraInicio;

    boolean sosActual() {
        return fechaHoraFin == null;
    }
}
