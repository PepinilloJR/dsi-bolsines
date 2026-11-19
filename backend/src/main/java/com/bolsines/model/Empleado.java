package com.bolsines.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empleados")
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

    public boolean sosGCM() {
        return this.rol != null && this.rol.esGCM();
    }

    // Getters y Setters estándar

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Rol getRol() {
        return this.rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public ComisionMedica getComisionMedica() {
        return this.comisionMedica;
    }

    public void setComisionMedica(ComisionMedica comisionMedica) {
        this.comisionMedica = comisionMedica;
    }
}