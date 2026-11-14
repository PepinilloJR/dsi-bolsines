package com.bolsines.demo.Domain;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Entity 
@Table(name = "bolsines")
@RequiredArgsConstructor
public class Bolsin {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    @NonNull
    Integer numeroBolsin;

    Integer numeroPrecinto;

    Double peso;

    @OneToMany(mappedBy = "bolsin", cascade = CascadeType.ALL)
    List<CambioEstado> cambiosEstado;

    @OneToOne(fetch = FetchType.EAGER)
    ComisionMedica origen;


    @OneToOne(fetch = FetchType.EAGER)
    ComisionMedica destino;
    @NonNull
    Double latitud;
    @NonNull
    Double longitud;
    @NonNull

    LocalDateTime fecha;

    public boolean esTuCMOrigen(ComisionMedica cm) {
        return this.origen.equals(cm);
    }

    public Integer getNumeroBolsin() {
        return this.numeroBolsin;
    }

    public Integer obtenerNumeroBolsin() {
        return getNumeroBolsin();
    }

    public boolean sosEnviado() {
        for (CambioEstado cambio : cambiosEstado) {
            if (cambio.sosActual()) {
                return cambio.sosEnviado();
            }
        }
        return false;
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
    // editar segun corresponda, metodo para la maquina de estados??


    //TODO:
    // Metodos de la maquina de estados

    public void cerrarBolsin() {
        // Cierra el bolsín y realiza las acciones necesarias
        // Por ejemplo, cambiar el estado a "Cerrado" o realizar validaciones finales
    }

    public void abrirBolsin() {
        // Abre el bolsín y realiza las acciones necesarias
        // Por ejemplo, cambiar el estado a "Abierto" o realizar validaciones iniciales
    }

    public void darDeBajaBolsin() {
        // Da de baja el bolsín y realiza las acciones necesarias
        // Por ejemplo, cambiar el estado a "Dado de Baja" o realizar validaciones finales
    }

    public void registrarBolsinEnviado() {
        // Registra el bolsín como enviado y realiza las acciones necesarias
        // Por ejemplo, cambiar el estado a "Enviado" o realizar validaciones finales
    }

    public void registrarRecepcionDeBolsin() {
        // Registra la recepción del bolsín y realiza las acciones necesarias
        // Por ejemplo, cambiar el estado a "Recibido" o realizar validaciones finales
    }


}
