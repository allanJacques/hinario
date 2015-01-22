package org.hinario.managedbean;

import org.hinario.app.AppMessage;
import org.hinario.app.ModoEditor;

public class ManagedBeanBase<F> {
	protected AppMessage appMessage;
	protected ModoEditor modoEditor;
	protected F filtro;
	protected boolean exibindoFiltro;

	public ManagedBeanBase() {
		this.appMessage = new AppMessage();
		this.modoEditor = ModoEditor.ADICAO;
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

	public F getFiltro() {
		return filtro;
	}

	public void setFiltro(F filtro) {
		this.filtro = filtro;
	}

	public boolean isExibindoFiltro() {
		return exibindoFiltro;
	}

	public void setExibindoFiltro(boolean exibindoFiltro) {
		this.exibindoFiltro = exibindoFiltro;
	}

}
