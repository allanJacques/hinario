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

@Entity
public class Consolador extends EntidadeBase implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private Irmao irmao;

	public Consolador() {
		this.irmao = new Irmao();
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Irmao getIrmao() {
		return irmao;
	}

	public void setIrmao(Irmao irmao) {
		this.irmao = irmao;
	}

}
