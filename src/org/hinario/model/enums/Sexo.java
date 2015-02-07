package org.hinario.model.enums;

import org.hinario.app.AppMessage;

public enum Sexo {
	MASCULINO(AppMessage.getStaticString("enum.sexo.masculino")), FEMININO(AppMessage.getStaticString("enum.sexo.feminino"));

	private Sexo(final String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.getDescricao();
	}
}
