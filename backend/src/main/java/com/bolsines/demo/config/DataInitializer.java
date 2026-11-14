package com.bolsines.demo.config;

import com.bolsines.demo.Domain.Bolsin;
import com.bolsines.demo.Repository.IBolsinRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final IBolsinRepository repository;

    public DataInitializer(IBolsinRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {

        List<Bolsin> bolsines = List.of(
                new Bolsin(101, -31.4201, -64.1888, LocalDateTime.of(2026, 3, 7, 12, 30, 0)),
                new Bolsin(102, -32.9468, -60.6393, LocalDateTime.of(2026, 3, 7, 12, 32, 15)),
                new Bolsin(103, -32.8895, -68.8458, LocalDateTime.of(2026, 3, 7, 12, 35, 4)),
                new Bolsin(104, -34.6037, -58.3816, LocalDateTime.of(2026, 3, 7, 12, 36, 20)),
                new Bolsin(105, -26.8083, -65.2176, LocalDateTime.of(2026, 3, 7, 12, 40, 31)),
                new Bolsin(106, -24.7829, -65.4232, LocalDateTime.of(2026, 3, 7, 12, 43, 18)),
                new Bolsin(107, -38.9516, -68.0591, LocalDateTime.of(2026, 3, 7, 12, 45, 46)),
                new Bolsin(108, -38.0055, -57.5426, LocalDateTime.of(2026, 3, 7, 12, 47, 2)),
                new Bolsin(109, -34.9215, -57.9545, LocalDateTime.of(2026, 3, 7, 12, 49, 27)),
                new Bolsin(110, -31.4167, -64.1833, LocalDateTime.of(2026, 3, 7, 12, 52, 9))
        );

        for (Bolsin bols : bolsines) {
            if (!repository.existsById(bols.getNumeroBolsin())) {
                repository.save(bols);
            }
        }
    }
}