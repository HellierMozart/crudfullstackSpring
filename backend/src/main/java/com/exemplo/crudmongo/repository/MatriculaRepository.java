package com.exemplo.crudmongo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exemplo.crudmongo.model.Matricula;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> 
{    
}
