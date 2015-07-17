package org.hinario.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Configuracao implements Serializable {

	private static final long serialVersionUID = 1L;

	public Configuracao() {
	}

	public Configuracao(String nome, String valor) {
		super();
		this.nome = nome;
		this.valor = valor;
	}

	@Id
	@NotNull
	@Column(length = 100, updatable = false, unique = true)
	private String nome;

	@NotNull
	@Column(length = 200)
	private String valor;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
