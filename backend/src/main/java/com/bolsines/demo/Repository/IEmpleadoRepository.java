package com.bolsines.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bolsines.demo.Domain.Empleado;

public interface IEmpleadoRepository extends JpaRepository<Empleado, Integer> {

}
