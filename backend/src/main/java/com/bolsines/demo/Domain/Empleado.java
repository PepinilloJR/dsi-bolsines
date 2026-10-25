package com.bolsines.demo.Domain;

public class Empleado {
    String apellido;
    String mail;
    String nombre;
    Rol rol;
    ComisionMedica comisionMedica;

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
        // cambiar
        return true;
    }
}
