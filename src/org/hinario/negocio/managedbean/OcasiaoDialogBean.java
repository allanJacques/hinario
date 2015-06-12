package org.hinario.negocio.managedbean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class OcasiaoDialogBean extends OcasiaoBean {

	private static final long serialVersionUID = 1L;

	public void salvar() {
		this.dao.salvar(this.getOcasiao());
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, this.appMessage.getString("message.sucesso"), this.appMessage.getString("message.salvoComSucesso"));
		FacesContext.getCurrentInstance().addMessage(null, fm);
		this.visualizando();
	}

}
