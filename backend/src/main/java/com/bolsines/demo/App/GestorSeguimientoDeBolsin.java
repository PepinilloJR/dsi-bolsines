
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
import com.bolsines.demo.Repository.ISesionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class GestorSeguimientoDeBolsin {

    private final ISesionRepository sesionRepository;

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
            IBolsinRepository bolsinRepository,
            IEmpleadoRepository empleadoRepository,
            InterfazGPSTracker interfazGPSTracker,
            InterfazServicioGoogleMaps interfazServicioGoogleMaps,
            ISesionRepository sesionRepository) {

        this.bolsinRepository = bolsinRepository;
        this.empleadoRepository = empleadoRepository;
        this.interfazGPSTracker = interfazGPSTracker;
        this.interfazServicioGoogleMaps = interfazServicioGoogleMaps;
        this.sesionRepository = sesionRepository;
    }

    // 1. Buscar Comisión Médica del empleado actual

    public ComisionMedicaResponse buscarCMActualEmpleado() {

        Usuario usuario = sesionRepository.findFirstByFechaHoraFinIsNullOrderByFechaHoraInicioDesc()
                .map(Sesion::getUsuarioLogueado)
                .orElseThrow(() -> new IllegalStateException("No existe una sesión activa"));

        Empleado empleado = usuario.getUsuario();

        this.CMOrigen = empleado.getCM();

        return new ComisionMedicaResponse(
                CMOrigen.getCodigo(),
                CMOrigen.getNombre());
    }

    // 2. Buscar bolsines enviados

    public List<BolsinResponse> buscarBolsinesEnviados() {

        List<Bolsin> bolsines = bolsinRepository.findAll();

        this.bolsinesEnviados = new ArrayList<>();

        List<BolsinResponse> numerosBolsines = new ArrayList<>();

        for (Bolsin bolsin : bolsines) {

            if (bolsin.sosEnviado() && bolsin.esTuCMOrigen(CMOrigen)) {

                this.bolsinesEnviados.add(bolsin);

                numerosBolsines.add(
                        new BolsinResponse(
                                bolsin.obtenerNumeroBolsin()));
            }
        }

        return numerosBolsines;
    }

    // 3. Buscar localizaciones mediante GPS Tracker y Adapter

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

                respuesta.add(
                        new LocalizacionBolsinResponse(
                                (Integer) datos[0],
                                (Double) datos[1],
                                (Double) datos[2],
                                (LocalDateTime) datos[3]));
            }
        }

        this.localizacionesBolsines = localizaciones.toArray(new Object[0][]);

        return respuesta;
    }

    // 4. Obtener mapa mediante Google Maps
    // Pendiente de implementar la interfaz

    public MapaResponse obtenerMapa() {

        /*
         * String mapa = interfazServicioGoogleMaps.obtenerMapa(
         * localizacionesBolsines
         * );
         * 
         * return new MapaResponse(mapa);
         */

        throw new UnsupportedOperationException(
                "Aun no implementado.");
    }

    // 5. Tomar selección de bolsín en el mapa

    public void tomarSeleccionBolsinMapa(Integer numeroBolsin) {

        this.bolsinesSeleccionado = bolsinesEnviados.stream()
                .filter(bolsin -> bolsin.obtenerNumeroBolsin().equals(numeroBolsin))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el bolsín seleccionado"));
    }

    // 6. Obtener correo del GCM de la Comisión Médica destino

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

    // 7. Tomar confirmación del correo

    public void tomarConfirmacionCorreo(Boolean confirmacion) {

        if (Boolean.TRUE.equals(confirmacion)) {

            this.CMDestino = bolsinesSeleccionado.getDestino();

            String emailGerente = obtenerEmailGerente();

            // Pendiente: llamarCU31(emailGerente);
        }
    }
}
