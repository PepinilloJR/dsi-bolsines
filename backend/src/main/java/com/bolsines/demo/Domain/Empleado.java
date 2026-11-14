package com.bolsines.demo.Domain;

import jakarta.persistence.*;

@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    private String apellido;

    private String mail;

    private String nombre;

    @Enumerated(EnumType.STRING)
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
}