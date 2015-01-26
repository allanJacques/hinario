package org.hinario.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hinario.dao.filtro.CampoNaoFiltravel;

@Entity
public class Irmao extends Entidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@CampoNaoFiltravel
	private Long id;

	@NotNull
	@Column(length = 100)
	private String nome;

	@Column(length = 1000)
	private String observacao;

	@OneToOne(mappedBy = "irmao", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@CampoNaoFiltravel
	private Usuario usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}
