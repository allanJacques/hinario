package org.hinario.negocio.arquivo;

import org.hinario.app.AppMessage;

public enum TipoArquivo {
	DOCUMENTO(AppMessage.getStaticString("enum.tipoArquivo.documento")), /**/
	AUDIO(AppMessage.getStaticString("enum.tipoArquivo.audio")), /**/
	VIDEO(AppMessage.getStaticString("enum.tipoArquivo.video")), /**/
	IMAGEM(AppMessage.getStaticString("enum.tipoArquivo.imagem")); /**/

	private TipoArquivo(String descricao) {
		this.descricao = descricao;
	}

	public String descricao;
}
