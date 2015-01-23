package org.hinario.util.filtro;

import org.hinario.app.AppMessage;

public enum Operador {

	
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
	IGUAL(			1,  AppMessage.getStaticString(			"filtro.condicao.igual"			)), 
	CONTEM(			2,  AppMessage.getStaticString(			"filtro.condicao.contem"		)), 
	NAOCONTEM(		3,  AppMessage.getStaticString(			"filtro.condicao.naoContem"		)), 
	COMECACOM(		4,  AppMessage.getStaticString(			"filtro.condicao.comecaCom"		)), 
	TERMINACOM(		5,  AppMessage.getStaticString(			"filtro.condicao.terminaCom"	)), 
	CONTEMPALAVRAS(	6,  AppMessage.getStaticString(			"filtro.condicao.contemPalavras")), 
	MAIOR(			7,  AppMessage.getStaticString(			"filtro.condicao.maior"			)),
	MAIORIGUAL(		8,  AppMessage.getStaticString(			"filtro.condicao.maiorIgual"	)), 
	MENOR(			9,  AppMessage.getStaticString(			"filtro.condicao.menor"			)), 
	MENORIGUAL(		10, AppMessage.getStaticString(			"filtro.condicao.menorIgual"	)), 
	DIFERENTE(		11, AppMessage.getStaticString(			"filtro.condicao.diferente"		));

	private String descricao;
	private Integer codigo;

	private Operador(final Integer codigo,final String descricao) {
		this.descricao = descricao;
		this.codigo = codigo;
	}
	
	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public String toString(){
		return this.descricao;
	}
}
