package com.bolsines.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
public class Empleado {

    @Id
    @Column(nullable = false, unique = true)
    private String email;

    private String apellido;

    private String nombre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comision_medica_codigo")
    private ComisionMedica comisionMedica;

    public Empleado(
            String apellido,
            String email,
            String nombre,
            Rol rol,
            ComisionMedica comisionMedica) {
        this.apellido = apellido;
        this.email = email;
        this.nombre = nombre;
        this.rol = rol;
        this.comisionMedica = comisionMedica;
    }

    public Boolean esTuCM(ComisionMedica cm) {
        return this.comisionMedica != null && this.comisionMedica.equals(cm);
    }

    public ComisionMedica getCM() {
        return this.comisionMedica;
    }

    public String getEmail() {
        return this.email;
    }

    public boolean sosGCM() {
        return this.rol != null && this.rol.esGCM();
    }
}