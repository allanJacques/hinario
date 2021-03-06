package org.hinario.model.enums;

import org.hinario.app.AppMessage;

/**
 * @author Allan Jacques
 */
public enum Sexo implements EnumBase {
	MASCULINO(0, AppMessage.getStaticString("enum.sexo.masculino")), /**/
	FEMININO(1, AppMessage.getStaticString("enum.sexo.feminino")); /**/

	private Sexo(final int chaveP, final String descricaoP) {
		descricao = descricaoP;
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
		return descricao;
	}
}
