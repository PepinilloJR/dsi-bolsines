package com.bolsines.dto.external;

// import java.time.LocalDateTime;

public record XtrBolsinLocationItemDto(
        Integer numeroBolsin,
        Double latitud,
        Double longitud,
        String fechaHoraUltimaActualizacion
) {
}