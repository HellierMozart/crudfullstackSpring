package com.exemplo.crudmongo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "disciplina")
public class Disciplina{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long 	id;
	private String 	nome;
	private int 	cargaHoraria;
	private boolean ativo;

	public Disciplina()
	{
	}

	public String	getNome() { return nome; }
	public void		setNome( String nome ) { this.nome = nome; }
	public int		getCargaHoraria() { return cargaHoraria; }
	public void 	setCargaHoraria( int cargaHoraria ) { this.cargaHoraria = cargaHoraria; }
	public boolean 	getAtivo() { return ativo; }
	public void 	setAtivo( boolean ativo ) { this.ativo = ativo; }
};
