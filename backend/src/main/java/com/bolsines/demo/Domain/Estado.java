package com.bolsines.demo.Domain;

public class Estado {
    String ambito;
    String descripcion;
    String nombre;

    public boolean esEnviado() {
        return this.nombre.equals("Enviado");
    }
}


// Todo listo