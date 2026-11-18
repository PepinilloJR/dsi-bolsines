package com.bolsines.repository;

import com.bolsines.model.Bolsin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IBolsinRepository extends JpaRepository<Bolsin, Long> {

    @Query("SELECT DISTINCT b FROM Bolsin b " +
        "LEFT JOIN FETCH b.origen " +
        "LEFT JOIN FETCH b.destino " +
        "LEFT JOIN FETCH b.cambiosEstado ce " +
        "LEFT JOIN FETCH ce.estado")
    List<Bolsin> findAllConEstados();
}