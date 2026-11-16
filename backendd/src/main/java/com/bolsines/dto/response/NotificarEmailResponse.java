package com.bolsines.dto.response;

public record NotificarEmailResponse(
        boolean ok,
        String destinatario
) {
}
