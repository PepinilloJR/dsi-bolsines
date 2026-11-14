package com.bolsines.demo.Domain;

import java.time.LocalDateTime;

public class Sesion {
    LocalDateTime fechaHoraFin;
    LocalDateTime fechaHoraInicio;
    Usuario usuario;

    public Usuario getUsuarioLogueado() {
        this.usuario.getUsuario().getCM().getDatos();
        return usuario;
    }

    // todo listo
}
