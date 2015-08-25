package org.hinario.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hinario.model.enums.Motivo;

@Entity
public class NotificacaoCanticoEmail extends EntidadeBase implements Serializable {

	private static final long serialVersionUID = 1L;

	public NotificacaoCanticoEmail() {
		this.dataAlteracao = new Date();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(cascade = { CascadeType.ALL }, optional = false)
	private Cantico cantico;

	@NotNull
	@Column(nullable = false, updatable = false)
	private Date dataAlteracao;

	@Column(insertable = false)
	private Date dataEnvio;

	@NotNull
	@Column(updatable = false)
	private Motivo motivo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cantico getCantico() {
		return cantico;
	}

	public void setCantico(Cantico cantico) {
		this.cantico = cantico;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public Motivo getMotivo() {
		return motivo;
	}

	public void setMotivo(Motivo motivo) {
		this.motivo = motivo;
	}

}
