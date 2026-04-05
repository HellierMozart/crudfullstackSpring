package com.exemplo.crudmongo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "turma") 
public class Turma 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    id;
    private String  nome;
    private String     ano;
    private boolean ativo;

    public Turma()
    {
    }

    public String   getNome() { return nome; }
    public void     setNome( String nome) { this.nome = nome; } 
    public String      getAno() { return ano; }
    public void     setAno( String ano ) { this.ano = ano; }
    public boolean  getAtivo () { return ativo; }
    public void     setAtivo( boolean ativo ) { this.ativo = ativo; }
}
