package org.hinario.managedbean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hinario.app.AppMessage;
import org.hinario.app.ModoEditor;
import org.hinario.dao.filtro.Condicao;
import org.hinario.dao.filtro.Filtro;
import org.hinario.dao.filtro.StatusCondicao;
import org.hinario.model.EntidadeBase;

public abstract class ManagedBeanBase {
	protected EntidadeDataModel usuarioDataModel;
	protected AppMessage appMessage;
	protected ModoEditor modoEditor;
	protected Filtro filtro;
	protected boolean exibindoFiltro;
	protected Condicao condicaoAAdicionar;

	public ManagedBeanBase() {
		this.appMessage = new AppMessage();
		this.modoEditor = ModoEditor.ADICAO;
		this.condicaoAAdicionar = new Condicao();
		this.appMessage = new AppMessage();
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
		if (this.condicaoAAdicionar.getStatusCondicao().equals(StatusCondicao.SUCESSO)) {
			this.filtro.getCondicoes().add(this.condicaoAAdicionar);
			this.condicaoAAdicionar = new Condicao();
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, this.condicaoAAdicionar.getStatusCondicao().getMensagem(), " "));
		}
	}

	public void removerCondicao(final Condicao condicao) {
		this.filtro.getCondicoes().remove(condicao);
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

	public abstract void setEntidade(final EntidadeBase entidadePorId);

	public abstract EntidadeBase getEntidade();
}
