package com.bolsines.demo.Repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolsines.demo.Domain.ComisionMedica;
import com.bolsines.demo.Domain.Empleado;

public interface IEmpleadoRepository extends JpaRepository<Empleado, Integer> {

    Optional<Empleado> findByRol_Nombre(String nombre);
    List<Empleado> findByComisionMedica(ComisionMedica comisionMedica);
}