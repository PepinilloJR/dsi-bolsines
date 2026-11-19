package com.bolsines.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
// import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.context.event.ApplicationReadyEvent;
// import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.bolsines.dto.response.BolsinResponse;
import com.bolsines.dto.response.ComisionResponse;
import com.bolsines.dto.response.IniciarResponse;
import com.bolsines.dto.response.NotificarEmailResponse;
import com.bolsines.model.Bolsin;
import com.bolsines.model.ComisionMedica;
import com.bolsines.model.Empleado;
import com.bolsines.model.Sesion;
import com.bolsines.repository.IBolsinRepository;
import com.bolsines.repository.IEmpleadoRepository;
import com.bolsines.repository.ISesionRepository;

@Service
public class GestorSeguimientodeBolsin {

    private final ISesionRepository sesionRepository;
    private final IBolsinRepository bolsinRepository;
    private final IEmpleadoRepository empleadoRepository;
    private final InterfazGPSTracker interfazGPSTracker;
    private final InterfazServicioGoogleMaps interfazGoogleMaps;

    // Esto habría que agregarlo como atributo? 
    private List<Object[]> datosLocalizacionBolsines = new ArrayList<>();

    private final List<String> apiKeys = List.of(
        "sk_xtr_9F3a7C21bE6d4A10f2c8e5b7d913", // XTR-4500L
        "sk_nav_7b61d9e4c3a2485fae10c77d5b2f", // NavTrack QX-7A
        "sk_geo_f2d84a1c9e7345b8a6d1c3f0972e"  // GeoPulse MTR-900
    );

    //! =========== Atributos del diagrama =========================
    private Sesion sesionActual;
    private List<Bolsin> bolsinesEnviados;
    private ComisionMedica cmOrigen;

    private Bolsin bolsinSeleccionado;
    private List<Empleado> empleados;
    

    public GestorSeguimientodeBolsin(
        InterfazGPSTracker interfazGPSTracker,
        ISesionRepository sesionRepository,
        IBolsinRepository bolsinRepository,
        IEmpleadoRepository empleadoRepository,
        @org.springframework.beans.factory.annotation.Value("${google.maps.api-key:}") String googleApiKey) {

        this.interfazGPSTracker = interfazGPSTracker;
        this.interfazGoogleMaps = new InterfazServicioGoogleMaps(googleApiKey);
        this.sesionRepository = sesionRepository;
        this.bolsinRepository = bolsinRepository;
        this.empleadoRepository = empleadoRepository;
        this.bolsinesEnviados = new ArrayList<>();
        this.cmOrigen = null;
        this.bolsinSeleccionado = null;
    }

    // para recuperar en memoria inicialmente algunos de los datos semilla que coinciden con los
    // bolsines del ms gps dado por los profesores
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent() {
        cargarDatos();
    }
    /* @EventListener(ContextRefreshedEvent.class)
    public void onApplicationEvent(ContextRefreshedEvent event) {
        cargarDatos();
    } */

    private void cargarDatos() {
        UUID sesionTestId = UUID.fromString(System.getenv("SESION_ACTIVA"));
        this.sesionActual = sesionRepository.findById(sesionTestId).orElse(null);

        this.empleados = this.empleadoRepository.findAllConRolYComisionMedica();
    }

    public IniciarResponse consultarUbicacionBolsines() {
        ComisionMedica comisionResponse = this.buscarCMActualEmpleado();
        this.cmOrigen = comisionResponse;

        // retorna void y setea en memoria this.bolsinesEnviado
        this.buscarBolsinesEnviado();

        // envuelve metodos que llaman al adapter
        this.buscarLocalizacionesBolsines();

        // transformar para la respuesta y preparar una respuesta gigante unificaada, charlable si dividirlo
        List<BolsinResponse> bolsinesToDto = this.bolsinesEnviados.stream()
                .map(BolsinResponse::fromEntity)
                .toList();

        // terminar de ver la parte de Google
        Object[][] mapa = this.obtenerMapa();

        return new IniciarResponse(
            ComisionResponse.fromEntity(comisionResponse), 
            bolsinesToDto,
            mapa
        );
    }

    private ComisionMedica buscarCMActualEmpleado() {
        // check preventivo si no estaban los datos al iniciar no forma parte del diagrama
        if (this.sesionActual == null) { cargarDatos(); }
        if (this.sesionActual == null) { return null; }
        
        return this.sesionActual.getUsuarioLogueado();
    }

    // 2. Buscar Bolsines Enviados
    public void buscarBolsinesEnviado() {
        // Trae todos los bolsines con sus objetos relacionados en memoria
        List<Bolsin> bolsines = bolsinRepository.findAllConEstados();

        this.bolsinesEnviados.clear();            // limpiar lista sino se acumula en memoria.

        for (Bolsin bolsin : bolsines) {
            // Filtra por cambiosEstado con Estados en Enviado; 
            if (bolsin.sosEnviado() && bolsin.esTuCMOrigen(this.cmOrigen)) {
                bolsin.obtenerNumeroBolsin();
                this.bolsinesEnviados.add(bolsin);
            }
        }
    }

    // 3. Metodo que engloba y dispara el bucle para la obtencion de latitudes y longitudes
    private void buscarLocalizacionesBolsines() {
        this.datosLocalizacionBolsines.clear();    // limpiar lista sino se cachea..

        for (Bolsin b : this.bolsinesEnviados) {
            Object[] datosLoc = interfazGPSTracker.enviarDatosLocalizacion(
                    this.apiKeys.get(0),        // NO TOCAR - APIKEY AdaptadorDatosXTR4500L
                    b.getNumeroBolsin(),
                    this.cmOrigen.getCodigo()
            );

            if (datosLoc != null && datosLoc.length >= 4) {
                datosLocalizacionBolsines.add(datosLoc);

                /* / Actualizamos el Bolsin en memoria con los datos adaptados */
                // posicion[0] es el numero bolsin, esta bien, esta mal? que lo juzgue la historia
                b.setLatitud((Double) datosLoc[1]);
                b.setLongitud((Double) datosLoc[2]);
                b.setFecha((LocalDateTime) datosLoc[3]); 
            }
        }
    }

    private Object[][] obtenerMapa() {
        // 1. Armamos la matriz de 6 columnas que exige InterfazServicioGoogleMaps
        Object[][] matrizLocalizaciones = new Object[this.bolsinesEnviados.size()][6];

        for (int i = 0; i < this.bolsinesEnviados.size(); i++) {
            Bolsin b = this.bolsinesEnviados.get(i);
            matrizLocalizaciones[i][InterfazServicioGoogleMaps.nroBolsin] = b.getNumeroBolsin().intValue();
            matrizLocalizaciones[i][InterfazServicioGoogleMaps.latitud] = b.getLatitud();
            matrizLocalizaciones[i][InterfazServicioGoogleMaps.longitud] = b.getLongitud();
            matrizLocalizaciones[i][InterfazServicioGoogleMaps.fechaHoraUltimaActualizacion] =
                    b.getFecha() != null ? b.getFecha().toString() : null;
            matrizLocalizaciones[i][InterfazServicioGoogleMaps.nroPrecinto] = b.getNumeroPrecinto();
            matrizLocalizaciones[i][InterfazServicioGoogleMaps.codCMDestino] =
                    b.getDestino() != null ? b.getDestino().getCodigo() : null;
        }

        // 2. Llamamos a la interfaz de Google Maps (filtra ubicables y ajusta coordenadas)
        Object[][] mapa = this.interfazGoogleMaps.obtenerMapa(matrizLocalizaciones);

        return mapa;
    }


    // PASO SELEECIONAR BOLSIN
    public BolsinResponse tomarSeleccionBolsinMapa(Long numeroBolsin) {

        this.bolsinSeleccionado = this.bolsinesEnviados.stream()
            .filter(bolsin -> bolsin.obtenerNumeroBolsin().equals(numeroBolsin))
            .findFirst()
            .orElseThrow(() -> 
                new IllegalArgumentException("No se encontró el bolsín seleccionado")
            );
        
        return BolsinResponse.fromEntity(bolsinSeleccionado);
    }

    // PASO TOMAR CONFIRMACION DE CORREO
    public NotificarEmailResponse tomarConfirmacionCorreo() {
        String emailGerente = this.obtenerEmailGerente();

        /* Sistema: para informar número de bolsín, fecha y hora de última actualización, y las 
        coordenadas del bolsín, se incluye al Caso de Uso 31 Notificar ubicación de bolsín. Informa la 
        ejecución exitosa del caso de uso 31. Fin del caso de uso. */
        this.llamarCU31(
            emailGerente,        // String
            this.bolsinSeleccionado.getNumeroBolsin(),    // int
            this.bolsinSeleccionado.getFecha(),    // Datetime
            this.bolsinSeleccionado.getLatitud(),    // double
            this.bolsinSeleccionado.getLongitud()    // double
        );

        return new NotificarEmailResponse(
            true,
            emailGerente
        );
    }

    private String obtenerEmailGerente() {
        if (this.bolsinSeleccionado == null || this.empleados == null) {
            return null;
        }

        // obtiene CMDestino para filtrar despues
        ComisionMedica cm = this.bolsinSeleccionado.getCMDestino();

        // filtra el gerente
        Empleado gcm = null;
        for (Empleado emp : this.empleados) {
            if (emp.esTuCM(cm) && emp.sosGCM()) {
                gcm = emp;
                break;
            }
        }

        return (gcm != null) ? gcm.getEmail() : null;
    }


    

    private boolean llamarCU31(
        String emailGerente,
        Long numeroBolsin,    // int
        LocalDateTime fechaHoraUltimaActualizacion,    // datetime
        Double latitud,    // float | double
        Double longitud    // float | double
    ) {
        /* Sistema: para informar número de bolsín, fecha y hora de última actualización, y las 
        coordenadas del bolsín, se incluye al Caso de Uso 31 Notificar ubicación de bolsín. Informa la 
        ejecución exitosa del caso de uso 31. Fin del caso de uso. */
        String.format(
            "NOTIFICACIÓN CU-31 [UBICACIÓN DE BOLSÍN]: Se ha enviado un alerta al Gerente de la CM Destino (%s) " +
            "informando que el bolsín N° %d registró su última actualización el día %s en las coordenadas geográficas " +
            "[Lat: %.6f, Lon: %.6f]. Operación completada con éxito.",
            emailGerente,
            numeroBolsin,
            fechaHoraUltimaActualizacion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
            latitud,
            longitud
        );
        // InterfazCU(datos) ???

        return true;
    }
}
