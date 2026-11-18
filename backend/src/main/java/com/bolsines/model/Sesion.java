package com.bolsines.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sesiones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sesion {

    @Id
    private UUID id;

    private LocalDateTime fechaHoraFin;
    private LocalDateTime fechaHoraInicio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @PrePersist
    public void generarIdSiEsNulo() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }

    public ComisionMedica getUsuarioLogueado() {
        if (this.usuario != null) {
            Empleado emp = this.usuario.getUsuario();
            ComisionMedica cm = emp.getCM();
            // Ejecuta el mensaje del diagrama de secuencia
            if (cm != null) {
                cm.getDatos(); 
            }
            return cm;
        }
        return null;
    }


    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }


    /* public ComisionMedica getCMUsuarioLogueado() {
        return this.getUsuario().getCMUsuarioLogueado();
    } */
}