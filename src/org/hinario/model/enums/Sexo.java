package org.hinario.model.enums;

import org.hinario.app.AppMessage;

/**
 * @author AllanJacques
 *
 */
public enum Sexo {
	MASCULINO(0, AppMessage.getStaticString("enum.sexo.masculino")), FEMININO(1, AppMessage.getStaticString("enum.sexo.feminino"));

	private Sexo(final int chave, final String descricao) {
		this.descricao = descricao;
	}

	private String descricao;
	private int chave;

	public String getDescricao() {
		return descricao;
	}

	public int getChave() {
		return chave;
	}

	@Override
	public String toString() {
		return this.getDescricao();
	}
}
