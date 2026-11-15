package com.bolsines.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comisiones_medicas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComisionMedica {

    @Id
    @Column(nullable = false, unique = true)
    private String codigo;

    private String direccion;
    private String mail;
    private String nombre;
    private String telefono;

    public String getDatos() {
        return this.codigo + " - " + this.direccion + " - " + this.mail + " - " + this.nombre + " - " + this.telefono;
        // return this.codigo + " - " + this.direccion + " - " + this.mail + " - " + this.nombre + " - " + this.telefono;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public String getNombre() {
        return this.nombre;
    }
}