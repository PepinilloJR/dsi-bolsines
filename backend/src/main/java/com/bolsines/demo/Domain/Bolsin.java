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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "bolsines")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Bolsin {

    @Id
    // la generacion de id de bolsin es manual para poder mockear con los que estan
    // en el mock del gps
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    @NonNull
    private Integer numeroBolsin;

    private Integer numeroPrecinto;

    private Double peso;

    @OneToMany(mappedBy = "bolsin", cascade = CascadeType.ALL)
    private List<CambioEstado> cambiosEstado;

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

    public ComisionMedica getDestino() {
        return this.destino;
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
    // Metodos de la maquina de estados - hacer?

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
        // Por ejemplo, cambiar el estado a "Dado de Baja" o realizar validaciones
        // finales
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
