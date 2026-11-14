package com.bolsines.demo.Dto;

import java.time.LocalDateTime;

public class LocalizacionBolsinResponse {

    private Integer numeroBolsin;
    private Double latitud;
    private Double longitud;
    private LocalDateTime fechaHoraActualizacion;

    public LocalizacionBolsinResponse(
            Integer numeroBolsin,
            Double latitud,
            Double longitud,
            LocalDateTime fechaHoraActualizacion) {

        this.numeroBolsin = numeroBolsin;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fechaHoraActualizacion = fechaHoraActualizacion;
    }

    public Integer getNumeroBolsin() {
        return numeroBolsin;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public LocalDateTime getFechaHoraActualizacion() {
        return fechaHoraActualizacion;
    }
}