package com.bolsines.demo.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bolsines.demo.Domain.Sesion;

@Repository
public interface ISesionRepository extends JpaRepository<Sesion, Integer> {

    Optional<Sesion> findFirstByFechaHoraFinIsNullOrderByFechaHoraInicioDesc();
}