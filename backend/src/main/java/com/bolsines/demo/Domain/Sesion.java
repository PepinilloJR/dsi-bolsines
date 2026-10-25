package com.bolsines.demo.Domain;

import java.time.LocalDateTime;

public class Sesion {
    LocalDateTime fechaHoraFin;
    LocalDateTime fechaHoraInicio;
    Usuario usuario;

    public Usuario getUsuarioLogueado() {
        // 1. 
        this.usuario.getUsuario().getComisionMedica().getDatos();
        return usuario;
    }
}
