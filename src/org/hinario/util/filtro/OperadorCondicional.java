package org.hinario.util.filtro;

import org.hinario.app.AppMessage;

public enum OperadorCondicional {

	
	/*
	IGUAL(AppMessage.getStaticString(			"filtro.condicao.igual"			)), 
	CONTEM(AppMessage.getStaticString(			"filtro.condicao.contem"		)), 
	COMECACOM(AppMessage.getStaticString(		"filtro.condicao.comecaCom"		)), 
	TERMINACOM(AppMessage.getStaticString(		"filtro.condicao.terminaCom"	)), 
	CONTEMPALAVRAS(AppMessage.getStaticString(	"filtro.condicao.contemPalavras")), 
	MAIOR(AppMessage.getStaticString(			"filtro.condicao.maior"			)),
	MAIORIGUAL(AppMessage.getStaticString(		"filtro.condicao.maiorIgual"	)), 
	MENOR(AppMessage.getStaticString(			"filtro.condicao.menor"			)), 
	MENORIGUAL(AppMessage.getStaticString(		"filtro.condicao.menorIgual"	)), 
	DIFERENTE(AppMessage.getStaticString(		"filtro.condicao.diferente"		));
	  */
	IGUAL(AppMessage.getStaticString(			"filtro.condicao.igual"			)), 
	CONTEM(AppMessage.getStaticString(			"filtro.condicao.contem"		)), 
	COMECACOM(AppMessage.getStaticString(		"filtro.condicao.comecaCom"		)), 
	TERMINACOM(AppMessage.getStaticString(		"filtro.condicao.terminaCom"	)), 
	CONTEMPALAVRAS(AppMessage.getStaticString(	"filtro.condicao.contemPalavras")), 
	MAIOR(AppMessage.getStaticString(			"filtro.condicao.maior"			)),
	MAIORIGUAL(AppMessage.getStaticString(		"filtro.condicao.maiorIgual"	)), 
	MENOR(AppMessage.getStaticString(			"filtro.condicao.menor"			)), 
	MENORIGUAL(AppMessage.getStaticString(		"filtro.condicao.menorIgual"	)), 
	DIFERENTE(AppMessage.getStaticString(		"filtro.condicao.diferente"		));

	private String descricao;

	private OperadorCondicional(final String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public String toString(){
		return this.descricao;
	}
}
