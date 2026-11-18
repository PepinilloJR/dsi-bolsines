package com.bolsines.demo.Domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empleados")
@NoArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    private String apellido;

    private String mail;

    private String nombre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comision_medica_codigo")
    private ComisionMedica comisionMedica;

    public Boolean esTuCM(ComisionMedica cm) {
        return this.comisionMedica.equals(cm);
    }

    public ComisionMedica getCM() {
        return this.comisionMedica;
    }

    public String getEmail() {
        return this.mail;
    }

    public Boolean sosGCM() {
        return this.rol.esGCM();
    }

    public Empleado(
        String apellido,
        String mail,
        String nombre,
        Rol rol,
        ComisionMedica comisionMedica) {

    this.apellido = apellido;
    this.mail = mail;
    this.nombre = nombre;
    this.rol = rol;
    this.comisionMedica = comisionMedica;
}
}