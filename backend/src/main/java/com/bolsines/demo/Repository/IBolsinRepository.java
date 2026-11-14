package com.bolsines.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bolsines.demo.Domain.Bolsin;

@Repository
public interface IBolsinRepository extends JpaRepository<Bolsin, Integer> {

    List<Bolsin> findDistinctByCambiosEstado_FechaHoraFinIsNullAndCambiosEstado_Estado_Nombre(
            String nombre);
}