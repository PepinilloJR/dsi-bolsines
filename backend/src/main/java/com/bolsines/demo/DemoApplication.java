package com.bolsines.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.bolsines.demo.App.GestorSeguimientoDeBolsin;
import com.bolsines.demo.App.GestorSeguimientoDeBolsinFactory;
import com.bolsines.demo.Domain.Sesion;
import com.bolsines.demo.Repository.ISesionRepository;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public GestorSeguimientoDeBolsin gestorSeguimientoDeBolsin(
            ISesionRepository sesionRepository,
            GestorSeguimientoDeBolsinFactory factory) {

        Sesion sesionActual = sesionRepository
                .findFirstByFechaHoraFinIsNullOrderByFechaHoraInicioDesc()
                .orElseThrow(() ->
                        new IllegalStateException("No existe una sesión activa"));

        return factory.crearGestor(sesionActual);
    }
}