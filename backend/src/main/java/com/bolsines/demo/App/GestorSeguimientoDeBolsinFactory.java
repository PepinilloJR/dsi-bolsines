package com.bolsines.demo.App;

import com.bolsines.demo.Domain.Sesion;
import com.bolsines.demo.Repository.IBolsinRepository;
import com.bolsines.demo.Repository.IEmpleadoRepository;

import org.springframework.stereotype.Component;

@Component
public class GestorSeguimientoDeBolsinFactory {

    private final IBolsinRepository bolsinRepository;
    private final IEmpleadoRepository empleadoRepository;
    private final InterfazGPSTracker interfazGPSTracker;
    private final InterfazServicioGoogleMaps interfazServicioGoogleMaps;

    public GestorSeguimientoDeBolsinFactory(
            IBolsinRepository bolsinRepository,
            IEmpleadoRepository empleadoRepository,
            InterfazGPSTracker interfazGPSTracker,
            InterfazServicioGoogleMaps interfazServicioGoogleMaps) {

        this.bolsinRepository = bolsinRepository;
        this.empleadoRepository = empleadoRepository;
        this.interfazGPSTracker = interfazGPSTracker;
        this.interfazServicioGoogleMaps = interfazServicioGoogleMaps;
    }

    public GestorSeguimientoDeBolsin crearGestor(Sesion sesion) {

        return new GestorSeguimientoDeBolsin(
                sesion,
                bolsinRepository,
                empleadoRepository,
                interfazGPSTracker,
                interfazServicioGoogleMaps
        );
    }
}