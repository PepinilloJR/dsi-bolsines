package com.bolsines.demo.Domain;

public class ComisionMedica {
    Integer codigo;
    String direccion;
    String mail;
    String nombre;
    String telefono;

    public String getDatos() {
        return this.codigo + " - " + this.direccion + " - " + this.mail + " - " + this.nombre + " - " + this.telefono;
    }
}
