package org.hinario.managedbean;

import org.hinario.app.AppMessage;
import org.hinario.app.ModoEditor;
import org.hinario.util.filtro.Condicao;
import org.hinario.util.filtro.Filtro;

public class ManagedBeanBase {
	protected AppMessage appMessage;
	protected ModoEditor modoEditor;
	protected Filtro filtro;
	protected boolean exibindoFiltro;
	protected Condicao condicaoAAdicionar;

	public ManagedBeanBase() {
		this.appMessage = new AppMessage();
		this.modoEditor = ModoEditor.ADICAO;
		this.condicaoAAdicionar = new Condicao();
	}

	public void adicionando() {
		this.modoEditor = ModoEditor.ADICAO;
	}

	public void editando() {
		this.modoEditor = ModoEditor.EDICAO;
	}

	public boolean isAdicao() {
		return this.modoEditor == ModoEditor.ADICAO;
	}

	public boolean isEdicao() {
		return this.modoEditor == ModoEditor.EDICAO;
	}

	public void adicionarCondicao() {
		this.filtro.getCondicoes().add(this.condicaoAAdicionar);
		this.condicaoAAdicionar = new Condicao();
	}

	public AppMessage getAppMessage() {
		return appMessage;
	}

	public void setAppMessage(AppMessage appMessage) {
		this.appMessage = appMessage;
	}

	public ModoEditor getModoEditor() {
		return modoEditor;
	}

	public void setModoEditor(ModoEditor modoEditor) {
		this.modoEditor = modoEditor;
	}

	public void inverteFiltro() {
		this.exibindoFiltro = !this.exibindoFiltro;
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public boolean isExibindoFiltro() {
		return exibindoFiltro;
	}

	public void setExibindoFiltro(boolean exibindoFiltro) {
		this.exibindoFiltro = exibindoFiltro;
	}

	public Condicao getCondicaoAAdicionar() {
		return condicaoAAdicionar;
	}

	public void setCondicaoAAdicionar(Condicao condicaoAAdicionar) {
		this.condicaoAAdicionar = condicaoAAdicionar;
	}

}
