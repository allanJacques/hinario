package org.hinario.dao.filtro;

import org.hinario.app.AppMessage;

public enum StatusCondicao {

	CAMPO_OPERADOR_IMCOMPATIVEIS("message.condicao.campoOperadorIncompativeis"), VALOR_NULO("message.condicao.valorNulo"), SUCESSO("message.condicao.sucesso");

	private AppMessage appMessage = new AppMessage();

	private StatusCondicao(final String chaveMensagem) {
		this.chaveMensagem = chaveMensagem;
	}

	private String chaveMensagem;

	public String getMensagem() {
		return this.appMessage.getString(this.chaveMensagem);
	}

}
