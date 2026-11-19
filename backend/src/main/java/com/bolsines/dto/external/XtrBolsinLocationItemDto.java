package com.bolsines.dto.external;

public record XtrBolsinLocationItemDto(
        Integer numeroBolsin,
        Double latitud,
        Double longitud,
        String fechaHoraUltimaActualizacion
) {
}