package com.bolsines.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comisiones_medicas")
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
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}