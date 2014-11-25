package org.hinario.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Arquivo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(length = 100)
	private String Nome;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@NotNull
	private byte[] conteudo;

	@NotNull
	@Column(length = 5)
	private String extensao;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUpload;

	@ManyToOne()
	@JoinColumn()
	private Cantico cantico;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	@Basic(fetch = FetchType.LAZY)
	public byte[] getConteudo() {
		return conteudo;
	}

	public void setConteudo(byte[] conteudo) {
		this.conteudo = conteudo;
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}

	public Date getDataUpload() {
		return dataUpload;
	}

	public void setDataUpload(Date dataUpload) {
		this.dataUpload = dataUpload;
	}

}
