package org.hinario.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hinario.dao.filtro.anotacoes.CampoNaoFiltravel;
import org.hinario.dao.filtro.anotacoes.FiltroCamposPrincipais;

@Entity
public class Cantico extends EntidadeBase implements Serializable {

	private static final long serialVersionUID = 1L;

	public Cantico() {
		this.ocasioes = new ArrayList<>();
		this.arquivos = new ArrayList<>();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@OneToOne
	@FiltroCamposPrincipais
	private Consolador consolador;

	@OneToOne
	@FiltroCamposPrincipais
	private Recebedor recebedor;

	@ManyToOne(optional = false, cascade = { CascadeType.ALL })
	@CampoNaoFiltravel
	private ModoDeCantar modoDeCantar;

	@Column(length = 150)
	private String assunto;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Ocasiao> ocasioes;

	@Temporal(TemporalType.DATE)
	private Date dataRecebimento;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Column(nullable = false, updatable = false)
	private Date dataCadastro;

	@Column(length = 7000)
	@Lob
	private String observacao;

	@OneToMany(mappedBy = "cantico", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@CampoNaoFiltravel
	private List<Arquivo> arquivos;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Consolador getConsolador() {
		return consolador;
	}

	public void setConsolador(Consolador consolador) {
		this.consolador = consolador;
	}

	public Recebedor getRecebedor() {
		return recebedor;
	}

	public void setRecebedor(Recebedor recebedor) {
		this.recebedor = recebedor;
	}

	public ModoDeCantar getModoDeCantar() {
		return modoDeCantar;
	}

	public void setModoDeCantar(ModoDeCantar modoDeCantar) {
		this.modoDeCantar = modoDeCantar;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public List<Ocasiao> getOcasioes() {
		return ocasioes;
	}

	public void setOcasioes(List<Ocasiao> ocasioes) {
		this.ocasioes = ocasioes;
	}

	public Date getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<Arquivo> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<Arquivo> arquivos) {
		this.arquivos = arquivos;
	}

}
