package com.exemplo.crudmongo.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "disciplina")
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private boolean ativo;

    public Disciplina() {}

    public Disciplina(Long id, String nome, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.ativo = ativo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
