package com.bolsines.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bolsines.demo.Domain.Estado;

@Repository 
public interface IEstadoRepository extends JpaRepository<Estado, Integer> {
    
}
