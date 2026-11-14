package com.bolsines.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bolsines.demo.Domain.Estado;

public interface IEstadoRepository extends JpaRepository<Estado, Integer> {
    
}
