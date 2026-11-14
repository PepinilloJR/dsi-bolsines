package com.bolsines.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bolsines.demo.Domain.Sesion;

public interface ISesionRepository extends JpaRepository<Sesion, Integer> {

}
