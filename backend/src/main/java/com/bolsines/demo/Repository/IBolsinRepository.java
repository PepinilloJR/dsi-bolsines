package com.bolsines.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bolsines.demo.Domain.Bolsin;

public interface IBolsinRepository extends JpaRepository<Bolsin, Integer>  {
    
}
