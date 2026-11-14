package com.bolsines.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bolsines.demo.Domain.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

}
