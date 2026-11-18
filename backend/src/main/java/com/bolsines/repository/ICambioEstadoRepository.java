package com.bolsines.repository;

import com.bolsines.model.CambioEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICambioEstadoRepository extends JpaRepository<CambioEstado, Long> {
}