package com.bolsines.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bolsines.demo.Domain.Rol;

public interface IRolRepository extends JpaRepository<Rol, Integer> {

}
