package com.bolsines.service;

import java.time.LocalDateTime;
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
import com.bolsines.model.Bolsin;
import com.bolsines.model.ComisionMedica;
import com.bolsines.model.Sesion;
import com.bolsines.repository.IBolsinRepository;
import com.bolsines.repository.ISesionRepository;

@Service
public class GestorSeguimientodeBolsin {

    private final ISesionRepository sesionRepository;
    private final IBolsinRepository bolsinRepository;
    private final InterfazGPSTracker interfazGPSTracker;

    // Esto habría que agregarlo como atributo? 
    private List<Object[]> datosLocalizacionBolsines = new ArrayList<>();

    private final List<String> apiKeys = List.of(
        "sk_xtr_9F3a7C21bE6d4A10f2c8e5b7d913", // XTR-4500L
        "sk_nav_7b61d9e4c3a2485fae10c77d5b2f", // NavTrack QX-7A
        "sk_geo_f2d84a1c9e7345b8a6d1c3f0972e"  // GeoPulse MTR-900
    );

    private Sesion actual;
    private List<Bolsin> bolsinesEnviados;
    private ComisionMedica CMOrigen;
    
    public GestorSeguimientodeBolsin(
        InterfazGPSTracker interfazGPSTracker,
        ISesionRepository sesionRepository,
        IBolsinRepository bolsinRepository) {

        this.interfazGPSTracker = interfazGPSTracker;
        this.sesionRepository = sesionRepository;
        this.bolsinRepository = bolsinRepository;
        this.bolsinesEnviados = new ArrayList<>();
        this.CMOrigen = null;
    }

    // STUPID FETCH TO VALID THIS STUPID DIAGRAMS TO THIS FUCKING STUPID SUBJECT
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent() {
        cargarDatos();
    }
    /* @EventListener(ContextRefreshedEvent.class)
    public void onApplicationEvent(ContextRefreshedEvent event) {
        cargarDatos();
    } */

    private void cargarDatos() {
        UUID sesionTestId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        this.actual = sesionRepository.findById(sesionTestId).orElse(null);
    }

    public IniciarResponse consultarUbicacionBolsines() {
        ComisionMedica comisionResponse = this.buscarCMActualEmpleado();
        // por algun motivo aca quieren que guardemos esta referncia o es o parece qsy nadie sabe del verbo saber
        this.CMOrigen = comisionResponse;

        // retorna void y setea en memoria this.bolsinesEnviado (???????????????????????????)
        this.buscarBolsinesEnviado();

        // esto es el metodo que envuelve llamar a los metodos del adapter
        this.buscarLocalizacionesBolsines();

        // transformar para la respuesta y preparar una respuesta gigante unificaada
        // si alguno no le gusta que me avise asi lo mando a la mierda, no mentira es charlable
        // pero como realmente depende de modificar el diagrama prefiero que devuelva todo de una me entendes
        // o sea es para simplificarnos la vida, yo codeo para vos, para ganar, por blg, por messi
        List<BolsinResponse> bolsinesToDto = this.bolsinesEnviados.stream()
                .map(BolsinResponse::fromEntity)
                .toList();

        // terminar de ver la parte de Google, realmente no hace falta que haga nada porq ue el front 
        // trabaja con latitudes y longitudes pero no se que hizo joaco capaz salio algo bueno quien sabe
        // que sabre yo
        String googleService = "Aca Llamamos a Google";

        return new IniciarResponse(
            ComisionResponse.fromEntity(comisionResponse), 
            bolsinesToDto,
            googleService
        );
    }

    private ComisionMedica buscarCMActualEmpleado() {
        // stupid check
        if (this.actual == null) {
            cargarDatos();
        }
        if (this.actual == null) {
            return null;
        }
        return this.actual.getUsuarioLogueado();
        // ComisionMedica cm = this.actual.getCMUsuarioLogueado();
    }

    // 2. Buscar bolsines enviados
    public void buscarBolsinesEnviado() {
        // trae todos los bolsines precargados con joins a los cambio estado y los estados 
        // asi poder filtrar todo en memoria, porque claro para que vamos a filtrar en la base 
        // de datos que usamos para persistir total 
        List<Bolsin> bolsines = bolsinRepository.findAllConEstados();
        this.bolsinesEnviados.clear();    // limpiar lista sino se acumula..

        for (Bolsin bolsin : bolsines) {
            // filtra por cambios de estado con estados en true ; 
            if (bolsin.sosEnviado() && bolsin.esTuCMOrigen(this.CMOrigen)) {

                // aca solo por cumplir diagrama llamo a este metodo total y completamente inchequeable
                // si aca tenemos todo en memoria para que carajo quieren que retorne esto
                // porque si vamos al caso entonces de ultima guardo una List<int> con los ids de los bolsines
                // en lugar del bolsin en memoria, pero como con la memoria ram ajena somos todos generosos que viva la pepa
                bolsin.obtenerNumeroBolsin();
                this.bolsinesEnviados.add(bolsin);
            }
        }

        // MAPEO TOTALMENTE AL PEDO ASIQUE RETORNA VOID ESTA COSA, SI TOTAL EL GESTOR
        // SI SEÑORES EL GESTOR GUARDA EN SUS ATRIBUTO UNA REFERENCIA A UNA LISTA EN MEMORIA QUE VA A SER EDITADA POR 
        // CUALQUIERA QUE HAGA OTRA PETICION
        // mapea los bolsines enviados recien filtrados hacia los dto
        // actualmente mapea más de un dato habría que ver si lo limitmos a solo numeros
        /* return this.bolsinesEnviados.stream()
                .map(BolsinResponse::fromEntity)
                .toList(); */
    }

    

    private void buscarLocalizacionesBolsines() {

        this.datosLocalizacionBolsines.clear();

        for (Bolsin b : this.bolsinesEnviados) {
            Object[] datosLoc = interfazGPSTracker.enviarDatosLocalizacion(
                    this.apiKeys.get(0),        // NO TOCAR - APIKEY AdaptadorDatosXTR4500L
                    b.getNumeroBolsin(),
                    this.CMOrigen.getCodigo()
            );

            if (datosLoc != null && datosLoc.length >= 4) {
                datosLocalizacionBolsines.add(datosLoc);

                /* / Actualizamos el Bolsin en memoria con los datos adaptados */
                // cualquier cosa justificamos que nos sirven guardar las posiciones actuales en memoria
                // y no solamente consultarlo, que si es una mala practica que se vayan a cagar
                // porque todo el diagrama en si es una mala practica, mucho mas facil y rapido todo al bolsin y listo
                // posicion[0] es el numero bolsin, esta bien, esta mal? que lo juzgue la historia
                b.setLatitud((Double) datosLoc[1]);
                b.setLongitud((Double) datosLoc[2]);
                b.setFecha((LocalDateTime) datosLoc[3]); 
            }
        }
    }
}
