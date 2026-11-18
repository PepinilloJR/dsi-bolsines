package com.bolsines.repository;

import com.bolsines.model.ComisionMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IComisionMedicaRepository extends JpaRepository<ComisionMedica, String> {
}