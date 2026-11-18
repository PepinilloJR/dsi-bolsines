
package com.bolsines.demo.config;

import com.bolsines.demo.Domain.*;
import com.bolsines.demo.Repository.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final IBolsinRepository bolsinRepository;
    private final IComisionMedicaRepository cmRepository;
    private final IEstadoRepository estadoRepository;
    private final IRolRepository rolRepository;
    private final IEmpleadoRepository empleadoRepository;
    private final IUsuarioRepository usuarioRepository;
    private final ISesionRepository sesionRepository;

    public DataInitializer(
            IBolsinRepository bolsinRepository,
            IComisionMedicaRepository cmRepository,
            IEstadoRepository estadoRepository,
            IRolRepository rolRepository,
            IEmpleadoRepository empleadoRepository,
            IUsuarioRepository usuarioRepository,
            ISesionRepository sesionRepository) {

        this.bolsinRepository = bolsinRepository;
        this.cmRepository = cmRepository;
        this.estadoRepository = estadoRepository;
        this.rolRepository = rolRepository;
        this.empleadoRepository = empleadoRepository;
        this.usuarioRepository = usuarioRepository;
        this.sesionRepository = sesionRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {

        // 1. Comisiones médicas

        ComisionMedica origen = cmRepository.findAll().stream()
                .filter(cm -> "CM Córdoba".equals(cm.getNombre()))
                .findFirst()
                .orElseGet(() -> cmRepository.save(
                        new ComisionMedica(
                                "Córdoba",
                                "cordoba@ejemplo.com",
                                "CM Córdoba",
                                "3510000000"
                        )
                ));

        ComisionMedica destino = cmRepository.findAll().stream()
                .filter(cm -> "CM Buenos Aires".equals(cm.getNombre()))
                .findFirst()
                .orElseGet(() -> cmRepository.save(
                        new ComisionMedica(
                                "Buenos Aires",
                                "buenosaires@ejemplo.com",
                                "CM Buenos Aires",
                                "1100000000"
                        )
                ));

        // 2. Estado Enviado

        Estado enviado = estadoRepository.findAll().stream()
                .filter(Estado::esEnviado)
                .findFirst()
                .orElseGet(() -> estadoRepository.save(
                        new Estado(
                                "Bolsin",
                                "Bolsín enviado",
                                "Enviado"
                        )
                ));

        // 3. Rol del empleado

        Rol rol = rolRepository.findAll().stream()
                .filter(r -> "Encargado de Bolsines".equals(r.getNombre()))
                .findFirst()
                .orElseGet(() -> rolRepository.save(
                        new Rol().newRol(
                                "Encargado de Bolsines",
                                "Seguimiento de bolsines"
                        )
                ));

        // 4. Empleado asociado a la CM de origen

        Empleado empleado = empleadoRepository.findAll().stream()
                .filter(e -> "empleado.prueba@ejemplo.com".equals(e.getEmail()))
                .findFirst()
                .orElseGet(() -> empleadoRepository.save(
                        new Empleado(
                                "Prueba",
                                "empleado.prueba@ejemplo.com",
                                "Empleado",
                                rol,
                                origen
                        )
                ));

        // 5. Usuario asociado al empleado

        Usuario usuario = usuarioRepository.findById("test")
                .orElseGet(() -> new Usuario(
                        "test",
                        "test123",
                        empleado
                ));

        usuario.setEmpleado(empleado);
        usuario = usuarioRepository.save(usuario);

        // 6. Sesión activa

        if (sesionRepository
                .findFirstByFechaHoraFinIsNullOrderByFechaHoraInicioDesc()
                .isEmpty()) {

            Sesion sesion = new Sesion(
                    null,
                    null,
                    LocalDateTime.now(),
                    usuario
            );

            sesionRepository.save(sesion);
        }

        // 7. Datos del mock GPS

        Object[][] datos = {
                {101, -31.4201, -64.1888, LocalDateTime.of(2026, 3, 7, 12, 30, 0)},
                {102, -32.9468, -60.6393, LocalDateTime.of(2026, 3, 7, 12, 32, 15)},
                {103, -32.8895, -68.8458, LocalDateTime.of(2026, 3, 7, 12, 35, 4)},
                {104, -34.6037, -58.3816, LocalDateTime.of(2026, 3, 7, 12, 36, 20)},
                {105, -26.8083, -65.2176, LocalDateTime.of(2026, 3, 7, 12, 40, 31)},
                {106, -24.7829, -65.4232, LocalDateTime.of(2026, 3, 7, 12, 43, 18)},
                {107, -38.9516, -68.0591, LocalDateTime.of(2026, 3, 7, 12, 45, 46)},
                {108, -38.0055, -57.5426, LocalDateTime.of(2026, 3, 7, 12, 47, 2)},
                {109, -34.9215, -57.9545, LocalDateTime.of(2026, 3, 7, 12, 49, 27)},
                {110, -31.4167, -64.1833, LocalDateTime.of(2026, 3, 7, 12, 52, 9)}
        };

        // 8. Bolsines y cambios de estado

        for (Object[] dato : datos) {

            Integer numero = (Integer) dato[0];

            if (bolsinRepository.existsById(numero)) {
                continue;
            }

            LocalDateTime fecha = (LocalDateTime) dato[3];
            List<CambioEstado> cambios = new ArrayList<>();

            Bolsin bolsin = new Bolsin(
                    numero,
                    5000 + numero,
                    2.5,
                    cambios,
                    origen,
                    destino,
                    (Double) dato[1],
                    (Double) dato[2],
                    fecha
            );

            CambioEstado cambio = new CambioEstado(
                    fecha,
                    null,
                    enviado,
                    bolsin
            );

            cambios.add(cambio);
            bolsinRepository.save(bolsin);
        }

        System.out.println("Mock JPA inicializado correctamente.");
    }
}
