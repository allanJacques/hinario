package org.hinario.negocio.arquivo;

import java.io.Serializable;

import org.hinario.app.AppMessage;

public enum TipoArquivo implements Serializable {

	DOCUMENTO(AppMessage.getStaticString("enum.tipoArquivo.documento")), /**/
	AUDIO(AppMessage.getStaticString("enum.tipoArquivo.audio")), /**/
	VIDEO(AppMessage.getStaticString("enum.tipoArquivo.video")), /**/
	IMAGEM(AppMessage.getStaticString("enum.tipoArquivo.imagem")); /**/

	private TipoArquivo(String descricao) {
		this.descricao = descricao;
	}

	public String descricao;
}
