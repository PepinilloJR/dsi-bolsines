package com.bolsines.demo.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bolsines.demo.Domain.ComisionMedica;


@Repository
public interface IComisionMedicaRepository extends JpaRepository<ComisionMedica, Integer> {
    
}
