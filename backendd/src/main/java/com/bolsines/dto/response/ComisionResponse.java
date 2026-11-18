package com.bolsines.dto.response;

import com.bolsines.model.ComisionMedica;

public record ComisionResponse(
        String codigo,
        String direccion,
        String email,
        String nombre,
        String telefono
) {
    public static ComisionResponse fromEntity(ComisionMedica cm) {
        if (cm == null) {
            return null;
        }
        return new ComisionResponse(
                cm.getCodigo(),
                cm.getDireccion(),
                cm.getMail(),
                cm.getNombre(),
                cm.getTelefono()
        );
    }
}