package org.hinario.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "CONSOLADOR_UNICO_IRMAO", columnNames = "irmao_id"))
public class Consolador extends EntidadeBase implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@OneToOne
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
