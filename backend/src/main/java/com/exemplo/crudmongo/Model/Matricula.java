package com.exemplo.crudmongo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.exemplo.crudmongo.model.Pessoa;

import java.time.LocalDate;

import com.exemplo.crudmongo.model.Curso;

@Entity
@Table(name = "matricula")
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long        id;
    
    @ManyToOne
    @JoinColumn(name = "pessoaId")
    private Pessoa      pessoa;

    @ManyToOne
    @JoinColumn(name = "disciplinaId")
    private Curso  curso;
    private LocalDate   dataMatricula;
    private boolean     ativo;

    public Matricula()
    {
    }

    public Pessoa       getPessoa() { return pessoa; }
    public void         setPessoa( Pessoa pessoa ) { this.pessoa = pessoa; } 
    public Curso        getCurso() { return curso; }
    public void         setCurso( Curso curso ) { this.curso = curso; }
    public LocalDate    getDataMatricula() { return dataMatricula; }
    public void         setDataMatricula( LocalDate dataMatricula ) { this.dataMatricula = dataMatricula; }
    public boolean      getAtivo() { return ativo; }
    public void         setAtivo( boolean ativo ) { this.ativo = ativo; }
}
