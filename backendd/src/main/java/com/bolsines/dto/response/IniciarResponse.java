package com.bolsines.dto.response;

import java.util.List;

public record IniciarResponse(
    ComisionResponse comision,
    List<BolsinResponse> bolsines,
    String mapa
) {
}