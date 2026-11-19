package com.bolsines.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sesiones")
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

    // Getters y Setters estándar

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getFechaHoraFin() {
        return this.fechaHoraFin;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public LocalDateTime getFechaHoraInicio() {
        return this.fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}