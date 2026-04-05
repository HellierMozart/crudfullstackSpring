package com.exemplo.crudmongo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

import com.exemplo.crudmongo.model.Pessoa;
import com.exemplo.crudmongo.model.Disciplina;



@Entity
@Table(name = "avaliacao")
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long        id;
    
    @ManyToOne
    @Column(name = "pessoaId")
    private Pessoa      pessoa;
    
    @ManyToMany
    @Column(name = "disciplinaId")
    private Disciplina  disciplina;
    private double      nota;
    private LocalDate   data;
    private boolean     ativo;

    public Avaliacao()
    {
    }

    public Pessoa       getPessoa() { return pessoa; }
    public void         setPessoa( Pessoa pessoa ) { this.pessoa = pessoa; }
    public Disciplina   getDisciplina() { return disciplina; }
    public void         setDisciplina( Disciplina disciplina ) { this.disciplina = disciplina; }
    public double       getNota() { return nota; }
    public void         setNota( double nota ) { this.nota = nota; }
    public LocalDate    getData() { return data; }
    public void         setData( LocalDate data ) { this.data = data; }
    public boolean      getAtivo() { return ativo; }
    public void         setAtivo( boolean ativo ) { this.ativo = ativo; }
 }
