package org.hinario.util.filtro;

public class Campo {

	private String nome;
	private String nomeAbsoluto;
	private String descricao;

	public Campo(String nome, String nomeAbsoluto, String descricao) {
		this.nome = nome;
		this.nomeAbsoluto = nomeAbsoluto;
		this.descricao = descricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeAbsoluto() {
		return nomeAbsoluto;
	}

	public void setNomeAbsoluto(String nomeAbsoluto) {
		this.nomeAbsoluto = nomeAbsoluto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return descricao;
	}

}
