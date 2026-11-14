package com.bolsines.demo.Dto;

public class ComisionMedicaResponse {

    private Integer codigo;
    private String nombre;

    public ComisionMedicaResponse(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }
}