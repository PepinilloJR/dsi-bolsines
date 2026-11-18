package com.bolsines.repository;

import com.bolsines.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmpleadoRepository extends JpaRepository<Empleado, String> {

    @Query("SELECT e FROM Empleado e " +
           "LEFT JOIN FETCH e.rol " +
           "LEFT JOIN FETCH e.comisionMedica")
    List<Empleado> findAllConRolYComisionMedica();
}