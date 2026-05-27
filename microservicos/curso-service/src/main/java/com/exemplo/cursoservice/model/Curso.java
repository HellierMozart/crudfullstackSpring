package com.exemplo.cursoservice.model;

import jakarta.persistence.*;

/**
 * Entidade Curso - pertence exclusivamente a este microserviço.
 * Baseia-se na entidade Curso do monolito.
 */
@Entity
@Table(name = "curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private int cargaHoraria;
    private boolean ativo;

    public Curso() {}

    public Curso(String nome, int cargaHoraria, boolean ativo) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.ativo = ativo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
