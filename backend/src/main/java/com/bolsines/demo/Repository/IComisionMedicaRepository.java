package com.bolsines.demo.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bolsines.demo.Domain.ComisionMedica;


public interface IComisionMedicaRepository extends JpaRepository<ComisionMedica, Integer> {
    
}
