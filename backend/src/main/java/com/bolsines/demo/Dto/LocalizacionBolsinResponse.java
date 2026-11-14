package com.bolsines.demo.Dto;

import java.time.LocalDateTime;

public record LocalizacionBolsinResponse(
        Integer numeroBolsin,
        Double latitud,
        Double longitud,
        LocalDateTime fechaHoraActualizacion
) {}