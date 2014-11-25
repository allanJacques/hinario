package org.hinario.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Cantico {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@OneToOne
	private Irmao consolador;

	@OneToOne
	private Irmao recebedor;

	@OneToOne
	private Ocasiao ocasiao;

	@Temporal(TemporalType.DATE)
	private Date dataRecebimento;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;

	@Column(length = 1000)
	private String observacao;

	@OneToMany(mappedBy = "cantico")
	private List<Arquivo> arquivos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Irmao getConsolador() {
		return consolador;
	}

	public void setConsolador(Irmao consolador) {
		this.consolador = consolador;
	}

	public Irmao getRecebedor() {
		return recebedor;
	}

	public void setRecebedor(Irmao recebedor) {
		this.recebedor = recebedor;
	}

	public Ocasiao getOcasiao() {
		return ocasiao;
	}

	public void setOcasiao(Ocasiao ocasiao) {
		this.ocasiao = ocasiao;
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
