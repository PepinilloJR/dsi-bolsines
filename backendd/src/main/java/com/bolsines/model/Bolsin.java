package com.bolsines.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bolsines")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Bolsin {

    @Id
    // Generación manual para mockear con los IDs del GPS
    @Column(nullable = false, unique = true)
    @NonNull
    private Long numeroBolsin;

    private Integer numeroPrecinto;

    private Double peso;

    @OneToMany(mappedBy = "bolsin", cascade = CascadeType.ALL)
    private List<CambioEstado> cambiosEstado = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "origen_codigo")
    private ComisionMedica origen;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destino_codigo")
    private ComisionMedica destino;

    @NonNull
    private Double latitud;

    @NonNull
    private Double longitud;

    @NonNull
    private LocalDateTime fecha;

    public boolean esTuCMOrigen(ComisionMedica cm) {
        return this.origen != null && this.origen.equals(cm);
    }

    public Long getNumeroBolsin() {
        return this.numeroBolsin;
    }

    public Long obtenerNumeroBolsin() {
        return getNumeroBolsin();
    }

    public boolean sosEnviado() {
        if (this.cambiosEstado == null) {
            return false;
        }
        // 
        for (CambioEstado cambio : this.cambiosEstado) {
            // delega por patron experto a cambio de estado de  cada uno
            // 
            if (cambio.sosActual()) {
                return cambio.sosEnviado();
            }
        }
        return false;
    }

    public ComisionMedica getDestino() {
        return this.destino;
    }

    public ComisionMedica getCMDestino() {
        return this.destino;
    }

    public Double getLatitud() {
        return this.latitud;
    }

    public Double getLongitud() {
        return this.longitud;
    }

    public LocalDateTime getFecha() {
        return this.fecha;
    }








    public void modificarBolsin(
            LocalDateTime fecha,
            Integer numeroPrecinto,
            Double peso,
            ComisionMedica origen,
            ComisionMedica destino) {
        this.fecha = fecha;
        this.numeroPrecinto = numeroPrecinto;
        this.peso = peso;
        this.origen = origen;
        this.destino = destino;
    }



    public void cerrarBolsin() {
    }

    public void abrirBolsin() {
    }

    public void darDeBajaBolsin() {
    }

    public void registrarBolsinEnviado() {
    }

    public void registrarRecepcionDeBolsin() {
    }
}