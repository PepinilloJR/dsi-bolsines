package com.bolsines.dto.response;

import com.bolsines.model.Bolsin;

import java.time.format.DateTimeFormatter;

public record BolsinResponse(
        String id,
        String fecha,
        String numeroBolsin,
        String numeroPrecinto,
        Double peso,
        Double latitud,
        Double longitud,
        String fechaHoraUltimaActualizacion,
        String codigoComisionMedicaOrigen,
        String codigoComisionMedicaDestino
) {
    public static BolsinResponse fromEntity(Bolsin bolsin) {
        if (bolsin == null) {
            return null;
        }

        String numeroStr = bolsin.getNumeroBolsin() != null ? bolsin.getNumeroBolsin().toString() : null;
        String fechaIso = bolsin.getFecha() != null
                ? bolsin.getFecha().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                : null;

        return new BolsinResponse(
                numeroStr,
                fechaIso,
                numeroStr,
                bolsin.getNumeroPrecinto() != null ? bolsin.getNumeroPrecinto().toString() : null,
                bolsin.getPeso(),
                bolsin.getLatitud(),
                bolsin.getLongitud(),
                fechaIso,
                bolsin.getOrigen() != null ? bolsin.getOrigen().getCodigo() : null,
                bolsin.getDestino() != null ? bolsin.getDestino().getCodigo() : null
        );
    }
}