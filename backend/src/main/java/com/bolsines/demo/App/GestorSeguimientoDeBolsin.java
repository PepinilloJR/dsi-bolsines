package com.bolsines.demo.App;

import com.bolsines.demo.Domain.Bolsin;
import com.bolsines.demo.Domain.ComisionMedica;
import com.bolsines.demo.Domain.Empleado;
import com.bolsines.demo.Domain.Sesion;
import com.bolsines.demo.Domain.Usuario;
import com.bolsines.demo.Repository.IBolsinRepository;
import com.bolsines.demo.Repository.IEmpleadoRepository;

import java.util.ArrayList;
import java.util.List;

public class GestorSeguimientoDeBolsin {

    private final Sesion sesionActual;

    private final IBolsinRepository bolsinRepository;
    private final IEmpleadoRepository empleadoRepository;
    private final InterfazGPSTracker interfazGPSTracker;

    private static final String API_KEY = "API_KEY_HARD_CODED";
    private Object[][] localizacionesBolsines;

    private List<Bolsin> bolsinesEnviados;
    private Bolsin bolsinesSeleccionado;
    private ComisionMedica CMOrigen;
    private ComisionMedica CMDestino;

    public GestorSeguimientoDeBolsin(
            Sesion sesionActual,
            IBolsinRepository bolsinRepository,
            IEmpleadoRepository empleadoRepository,
            InterfazGPSTracker interfazGPSTracker) {

        this.sesionActual = sesionActual;
        this.bolsinRepository = bolsinRepository;
        this.empleadoRepository = empleadoRepository;
        this.interfazGPSTracker = interfazGPSTracker;
    }

    public ComisionMedica buscarCMActualEmpleado() {

        Usuario usuario = sesionActual.getUsuarioLogueado();

        Empleado empleado = usuario.getUsuario();

        this.CMOrigen = empleado.getCM();

        return this.CMOrigen;
    }

    public List<Integer> buscarBolsinesEnviados() {

        List<Bolsin> bolsines = bolsinRepository.findAll();

        this.bolsinesEnviados = new ArrayList<>();
        List<Integer> numerosBolsines = new ArrayList<>();

        for (Bolsin bolsin : bolsines) {

            if (bolsin.sosEnviado() && bolsin.esTuCMOrigen(CMOrigen)) {

                this.bolsinesEnviados.add(bolsin);

                numerosBolsines.add(bolsin.obtenerNumeroBolsin());
            }
        }

        return numerosBolsines;
    }

    // adapter
    // ver bien como se resuelve
    public Object[][] buscarLocalizacionesBolsines() {

        List<Object[]> localizaciones = new ArrayList<>();

        for (Bolsin bolsin : bolsinesEnviados) {

            Object[][] datos = interfazGPSTracker.enviarDatosLocalizacion(
                    API_KEY,
                    bolsin.obtenerNumeroBolsin(),
                    CMOrigen.getCodigo());

            if (datos != null) {
                for (Object[] localizacion : datos) {
                    localizaciones.add(localizacion);
                }
            }
        }

        this.localizacionesBolsines = localizaciones.toArray(new Object[0][]);

        return this.localizacionesBolsines;
    }
}