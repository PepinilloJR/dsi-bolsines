package com.bolsines.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bolsines.demo.Domain.Usuario;

@Repository 
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

}
