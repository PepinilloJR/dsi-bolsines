package com.bolsines.demo.App;

import com.bolsines.demo.Domain.Bolsin;
import com.bolsines.demo.Domain.ComisionMedica;
import com.bolsines.demo.Domain.Empleado;
import com.bolsines.demo.Domain.Sesion;
import com.bolsines.demo.Domain.Usuario;
import com.bolsines.demo.Dto.BolsinResponse;
import com.bolsines.demo.Dto.ComisionMedicaResponse;
import com.bolsines.demo.Dto.LocalizacionBolsinResponse;
import com.bolsines.demo.Dto.MapaResponse;
import com.bolsines.demo.Repository.IBolsinRepository;
import com.bolsines.demo.Repository.IEmpleadoRepository;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GestorSeguimientoDeBolsin {

    private final Sesion sesionActual;

    private final IBolsinRepository bolsinRepository;
    private final IEmpleadoRepository empleadoRepository;
    private final InterfazGPSTracker interfazGPSTracker;
    private final InterfazServicioGoogleMaps interfazServicioGoogleMaps;

    private static final String API_KEY = "API_KEY_HARD_CODED";

    private Object[] localizacionesBolsines;

    private List<Bolsin> bolsinesEnviados;
    private Bolsin bolsinesSeleccionado;
    private ComisionMedica CMOrigen;
    private ComisionMedica CMDestino;

    public GestorSeguimientoDeBolsin(
            Sesion sesionActual,
            IBolsinRepository bolsinRepository,
            IEmpleadoRepository empleadoRepository,
            InterfazGPSTracker interfazGPSTracker,
            InterfazServicioGoogleMaps interfazServicioGoogleMaps) {

        this.sesionActual = sesionActual;
        this.bolsinRepository = bolsinRepository;
        this.empleadoRepository = empleadoRepository;
        this.interfazGPSTracker = interfazGPSTracker;
        this.interfazServicioGoogleMaps = interfazServicioGoogleMaps;
    }

    public ComisionMedicaResponse buscarCMActualEmpleado() {

        Usuario usuario = sesionActual.getUsuarioLogueado();

        Empleado empleado = usuario.getUsuario();

        this.CMOrigen = empleado.getCM();

        return new ComisionMedicaResponse(
                CMOrigen.getCodigo(),
                CMOrigen.getNombre());
    }

    public List<BolsinResponse> buscarBolsinesEnviados() {

        List<Bolsin> bolsines = bolsinRepository.findAll();

        this.bolsinesEnviados = new ArrayList<>();
        List<BolsinResponse> numerosBolsines = new ArrayList<>();

        for (Bolsin bolsin : bolsines) {

            if (bolsin.sosEnviado() && bolsin.esTuCMOrigen(CMOrigen)) {

                this.bolsinesEnviados.add(bolsin);

                numerosBolsines.add(
                        new BolsinResponse(bolsin.obtenerNumeroBolsin()));
            }
        }

        return numerosBolsines;
    }

    // adapter
    // ver bien como se resuelve
    public List<LocalizacionBolsinResponse> buscarLocalizacionesBolsines() {

        List<Object[]> localizaciones = new ArrayList<>();
        List<LocalizacionBolsinResponse> respuesta = new ArrayList<>();

        for (Bolsin bolsin : bolsinesEnviados) {

            Object[] datos = interfazGPSTracker.enviarDatosLocalizacion(
                    API_KEY,
                    bolsin.obtenerNumeroBolsin(),
                    CMOrigen.getCodigo());

            if (datos != null) {

                localizaciones.add(datos);

                respuesta.add(new LocalizacionBolsinResponse(
                        (Integer) datos[0],
                        (Double) datos[1],
                        (Double) datos[2],
                        (LocalDateTime) datos[3]));
            }
        }

        this.localizacionesBolsines = localizaciones.toArray();

        return respuesta;
    }

    // generico hasta implementacion de interfaz

    public MapaResponse obtenerMapa() {

        /*
         * String mapa = interfazServicioGoogleMaps.obtenerMapa(localizacionesBolsines);
         * return new MapaResponse(mapa);
         */
        throw new UnsupportedOperationException("Aun no implementado.");
    }

    public void tomarSeleccionBolsinMapa(Integer numeroBolsin) {

        this.bolsinesSeleccionado = bolsinesEnviados.stream()
                .filter(bolsin -> bolsin.obtenerNumeroBolsin().equals(numeroBolsin))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el bolsín seleccionado"));
    }

    private String obtenerEmailGerente() {

        List<Empleado> empleados = empleadoRepository.findByComisionMedica(CMDestino);

        for (Empleado empleado : empleados) {

            if (empleado.esTuCM(CMDestino) && empleado.sosGCM()) {
                return empleado.getEmail();
            }
        }

        throw new IllegalStateException(
                "No se encontró un GCM en la Comisión Médica de destino");
    }

    public void tomarConfirmacionCorreo(Boolean confirmacion) {

        if (Boolean.TRUE.equals(confirmacion)) {

            this.CMDestino = bolsinesSeleccionado.getDestino();

            String emailGerente = obtenerEmailGerente();

            // Pendiente: llamarCU31(emailGerente);
        }
    }

}