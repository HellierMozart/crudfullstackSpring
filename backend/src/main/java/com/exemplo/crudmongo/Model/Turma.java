package com.exemplo.crudmongo.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "turma")
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private int ano;
    private boolean ativo;

    public Turma() {}

    public Turma(Long id, String nome, int ano, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.ano = ano;
        this.ativo = ativo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
