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
	private String nome;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@NotNull
	private byte[] conteudo;

	@NotNull
	@Column(length = 5)
	private String mimeType;

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
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Basic(fetch = FetchType.LAZY)
	public byte[] getConteudo() {
		return this.conteudo;
	}

	public void setConteudo(byte[] conteudo) {
		this.conteudo = conteudo;
	}

	public String getMIMEType() {
		return mimeType;
	}

	public void setMIMEType(String MIMEType) {
		this.mimeType = MIMEType;
	}

	public Date getDataUpload() {
		return this.dataUpload;
	}

	public void setDataUpload(Date dataUpload) {
		this.dataUpload = dataUpload;
	}

}
