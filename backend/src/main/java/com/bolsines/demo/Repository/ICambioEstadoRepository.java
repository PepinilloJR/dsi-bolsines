package com.bolsines.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bolsines.demo.Domain.CambioEstado;

public interface ICambioEstadoRepository extends JpaRepository<CambioEstado, Integer> {
    
}
