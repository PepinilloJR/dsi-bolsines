package com.bolsines.config;

import com.bolsines.model.*;
import com.bolsines.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class DataSeedConfig {

    private final IRolRepository rolRepository;
    private final IEstadoRepository estadoRepository;
    private final IComisionMedicaRepository comisionMedicaRepository;
    private final IEmpleadoRepository empleadoRepository;
    private final IUsuarioRepository usuarioRepository;
    private final ISesionRepository sesionRepository;
    private final IBolsinRepository bolsinRepository;
    // private final ICambioEstadoRepository cambioEstadoRepository;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // 1. Roles
            Rol rolGcm = rolRepository.save(new Rol("GCM", "Gestor de Comisión Médica"));
            Rol rolOperador = rolRepository.save(new Rol("OPERADOR", "Operador Administrativo"));

            // 2. Estados
            Estado estadoAbierto = estadoRepository.save(new Estado("Bolsin", "Bolsín abierto en origen", "Abierto"));
            Estado estadoCerrado = estadoRepository.save(new Estado("Bolsin", "Bolsín cerrado y precintado", "Cerrado"));
            Estado estadoEnviado = estadoRepository.save(new Estado("Bolsin", "Bolsín en tránsito hacia destino", "Enviado"));
            estadoRepository.save(new Estado("Bolsin", "Bolsín recibido en destino", "Recibido"));

            // 3. Comisiones Médicas (todas las referenciadas en el mock del GPS)
            ComisionMedica cmCba01 = comisionMedicaRepository.save(new ComisionMedica(
                    "CM-CBA-01", "Av. Colón 550, Córdoba", "cba01@srt.gob.ar", "Comisión Médica Córdoba Centro", "0351-4210001"));
            ComisionMedica cmCba02 = comisionMedicaRepository.save(new ComisionMedica(
                    "CM-CBA-02", "Bv. Chacabuco 820, Córdoba", "cba02@srt.gob.ar", "Comisión Médica Córdoba Nueva", "0351-4210002"));
            ComisionMedica cmRos02 = comisionMedicaRepository.save(new ComisionMedica(
                    "CM-ROS-02", "Santa Fe 1420, Rosario", "ros02@srt.gob.ar", "Comisión Médica Rosario Norte", "0341-4402200"));
            ComisionMedica cmMdz03 = comisionMedicaRepository.save(new ComisionMedica(
                    "CM-MDZ-03", "San Martín 1150, Mendoza", "mdz03@srt.gob.ar", "Comisión Médica Mendoza", "0261-4231100"));
            ComisionMedica cmBa01 = comisionMedicaRepository.save(new ComisionMedica(
                    "CM-BA-01", "Moreno 401, CABA", "ba01@srt.gob.ar", "Comisión Médica Central CABA", "011-43210000"));
            ComisionMedica cmTuc01 = comisionMedicaRepository.save(new ComisionMedica(
                    "CM-TUC-01", "24 de Septiembre 600, Tucumán", "tuc01@srt.gob.ar", "Comisión Médica Tucumán", "0381-4225500"));
            ComisionMedica cmSla01 = comisionMedicaRepository.save(new ComisionMedica(
                    "CM-SLA-01", "Belgrano 780, Salta", "sla01@srt.gob.ar", "Comisión Médica Salta", "0387-4319900"));
            ComisionMedica cmJuj01 = comisionMedicaRepository.save(new ComisionMedica(
                    "CM-JUJ-01", "Lavalle 310, San Salvador de Jujuy", "juj01@srt.gob.ar", "Comisión Médica Jujuy", "0388-4228800"));
            ComisionMedica cmNqn01 = comisionMedicaRepository.save(new ComisionMedica(
                    "CM-NQN-01", "Av. Argentina 450, Neuquén", "nqn01@srt.gob.ar", "Comisión Médica Neuquén", "0299-4427700"));
            ComisionMedica cmMdq01 = comisionMedicaRepository.save(new ComisionMedica(
                    "CM-MDQ-01", "Av. Independencia 2100, Mar del Plata", "mdq01@srt.gob.ar", "Comisión Médica Mar del Plata", "0223-4956600"));
            ComisionMedica cmLpl01 = comisionMedicaRepository.save(new ComisionMedica(
                    "CM-LPL-01", "Calle 48 N° 630, La Plata", "lpl01@srt.gob.ar", "Comisión Médica La Plata", "0221-4824400"));
            ComisionMedica cmCba03 = comisionMedicaRepository.save(new ComisionMedica(
                    "CM-CBA-01", "Av. Emilio Olmos 550, Córdoba", "cba03@srt.gob.ar", "Comisión Médica Córdoba Centro Emilio Olmos", "0351-4210001"));

            // 4. Empleados (asignados a CM-CBA-01 y CM-ROS-02)
            Empleado emp1 = empleadoRepository.save(new Empleado(
                    "Pérez", "jperez@srt.gob.ar", "Juan", rolGcm, cmCba01));
            Empleado emp2 = empleadoRepository.save(new Empleado(
                    "Gómez", "mgomez@srt.gob.ar", "María", rolOperador, cmCba03));

            Empleado emp3 = empleadoRepository.save(new Empleado(
                    "Gerente", "gerente@srt.gob.ar", "Jorge rente", rolGcm, cmRos02));

            // 5. Usuarios
            Usuario user1 = usuarioRepository.save(new Usuario("jperez", "1234", emp1));
            Usuario user2 = usuarioRepository.save(new Usuario("mgomez", "1234", emp2));        // user 2
            usuarioRepository.save(new Usuario("mGerente", "1234", emp3));        // user 3

            // 6. Sesión activa (fechaHoraFin == null) para jperez (CM-CBA-01)
            Sesion sesionActiva = new Sesion();
            sesionActiva.setId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
            sesionActiva.setFechaHoraInicio(LocalDateTime.now().minusHours(1));
            sesionActiva.setFechaHoraFin(null);
            sesionActiva.setUsuario(user1);
            sesionActiva = sesionRepository.save(sesionActiva);

            Sesion sesionActiva2 = new Sesion();
            sesionActiva2.setId(UUID.fromString("11111111-1111-1111-1111-111111111112"));
            sesionActiva2.setFechaHoraInicio(LocalDateTime.now().minusHours(1));
            sesionActiva2.setFechaHoraFin(null);
            sesionActiva2.setUsuario(user2);
            sesionActiva2 = sesionRepository.save(sesionActiva2);

            // 7. Bolsines respetando exactamente los IDs, orígenes, destinos, coordenadas y fechas del mock GPS
            Bolsin b101 = crearBolsin(101L, 9001, 4.5, cmCba01, cmRos02, -31.4201, -64.1888, LocalDateTime.of(2026, 3, 7, 12, 30, 0), estadoEnviado);
            Bolsin b102 = crearBolsin(102L, 9002, 3.2, cmRos02, cmMdz03, -32.9468, -60.6393, LocalDateTime.of(2026, 3, 7, 12, 32, 15), estadoEnviado);
            Bolsin b103 = crearBolsin(103L, 9003, 5.1, cmMdz03, cmBa01,  -32.8895, -68.8458, LocalDateTime.of(2026, 3, 7, 12, 35, 4),  estadoEnviado);
            Bolsin b104 = crearBolsin(104L, 9004, 2.8, cmBa01,  cmCba01, -34.6037, -58.3816, LocalDateTime.of(2026, 3, 7, 12, 36, 20), estadoEnviado);
            Bolsin b105 = crearBolsin(105L, 9005, 6.0, cmTuc01, cmSla01, -26.8083, -65.2176, LocalDateTime.of(2026, 3, 7, 12, 40, 31), estadoEnviado);
            Bolsin b106 = crearBolsin(106L, 9006, 3.9, cmSla01, cmJuj01, -24.7829, -65.4232, LocalDateTime.of(2026, 3, 7, 12, 43, 18), estadoEnviado);
            Bolsin b107 = crearBolsin(107L, 9007, 4.1, cmNqn01, cmMdq01, -38.9516, -68.0591, LocalDateTime.of(2026, 3, 7, 12, 45, 46), estadoEnviado);
            Bolsin b108 = crearBolsin(108L, 9008, 7.4, cmMdq01, cmBa01,  -38.0055, -57.5426, LocalDateTime.of(2026, 3, 7, 12, 47, 2),  estadoEnviado);
            Bolsin b109 = crearBolsin(109L, 9009, 2.5, cmLpl01, cmCba01, -34.9215, -57.9545, LocalDateTime.of(2026, 3, 7, 12, 49, 27), estadoCerrado);
            Bolsin b110 = crearBolsin(110L, 9010, 5.0, cmCba02, cmRos02, -31.4167, -64.1833, LocalDateTime.of(2026, 3, 7, 12, 52, 9),  estadoAbierto);

            bolsinRepository.saveAll(List.of(b101, b102, b103, b104, b105, b106, b107, b108, b109, b110));

            System.out.println("=========================================================");
            System.out.println("DATA SEED CARGADO OK");
            System.out.println("UUID SESION ACTIVA (Header X-Sesion-Id): " + sesionActiva.getId());
            System.out.println("Usuario: " + user1.getNombreUsuario() + " | CM: " + emp1.getCM().getCodigo());
            System.out.println("=========================================================");
        };
    }

    private Bolsin crearBolsin(
            Long numero,
            Integer precinto,
            Double peso,
            ComisionMedica origen,
            ComisionMedica destino,
            Double latitud,
            Double longitud,
            LocalDateTime fecha,
            Estado estadoActual) {

        Bolsin bolsin = new Bolsin(numero, latitud, longitud, fecha);
        bolsin.setNumeroPrecinto(precinto);
        bolsin.setPeso(peso);
        bolsin.setOrigen(origen);
        bolsin.setDestino(destino);

        // CambioEstado actual (fechaHoraFin == null)
        CambioEstado cambio = new CambioEstado(fecha, null, estadoActual, bolsin);
        bolsin.getCambiosEstado().add(cambio);

        return bolsin;
    }
}