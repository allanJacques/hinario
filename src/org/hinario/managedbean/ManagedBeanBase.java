package org.hinario.managedbean;

import org.hinario.app.AppMessage;
import org.hinario.app.ModoEditor;

public class ManagedBeanBase {
	protected AppMessage appMessage;
	protected ModoEditor modoEditor;

	public ManagedBeanBase() {
		this.appMessage = new AppMessage();
		this.modoEditor = ModoEditor.ADICAO;
	}

	public boolean isAdicao() {
		return this.modoEditor == ModoEditor.ADICAO;
	}

	public boolean isEdicao() {
		return this.modoEditor == ModoEditor.EDICAO;
	}

	public boolean isNenhum() {
		return this.modoEditor == ModoEditor.NENHUM;
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

}
