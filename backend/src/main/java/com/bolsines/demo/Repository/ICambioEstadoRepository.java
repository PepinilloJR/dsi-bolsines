package com.bolsines.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bolsines.demo.Domain.CambioEstado;

@Repository 
public interface ICambioEstadoRepository extends JpaRepository<CambioEstado, Integer> {
    
}
