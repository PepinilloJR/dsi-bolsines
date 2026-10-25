package com.bolsines.demo.Domain;

import java.time.LocalDateTime;
import java.util.List;

public class Bolsin {
    LocalDateTime fecha;
    Integer numeroBolsin;
    Integer numeroPrecinto;
    Double peso;
    List<CambioEstado> cambiosEstado;
    ComisionMedica origen;
    ComisionMedica destino;
}
