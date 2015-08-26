package org.hinario.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hinario.dao.filtro.anotacoes.CampoNaoFiltravel;
import org.hinario.dao.filtro.anotacoes.CampoPrincipal;
import org.hinario.dao.filtro.anotacoes.NaoFiltravelPara;
import org.hinario.model.enums.Sexo;

@Entity
public class Irmao extends EntidadeBase implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@CampoNaoFiltravel
	private Long id;

	@NotNull
	@Column(length = 100, unique = true)
	@CampoPrincipal
	private String nome;

	@NotNull
	@Enumerated(value = EnumType.ORDINAL)
	@Column(length = 1, scale = 1)
	@NaoFiltravelPara({ Cantico.class })
	private Sexo sexo;

	@Column(length = 1000)
	@NaoFiltravelPara({ Cantico.class })
	private String observacao;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Column(updatable = false)
	@NaoFiltravelPara({ Cantico.class })
	private Date dataCadastro;

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

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

}
