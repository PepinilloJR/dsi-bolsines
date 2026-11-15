package com.bolsines.repository;

import com.bolsines.model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ISesionRepository extends JpaRepository<Sesion, UUID> {
    Optional<Sesion> findFirstByFechaHoraFinIsNullOrderByFechaHoraInicioDesc();
}
