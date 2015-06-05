package org.hinario.negocio.managedbean;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class ConsoladorDialogBean extends ConsoladorBean {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{canticoBean}")
	private CanticoBean canticoBean;

	@Override
	public void salvar() {
		try {
			this.consolador.getIrmao().setDataCadastro(new Date());
			dao.salvar(this.consolador);
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, this.appMessage.getString("message.sucesso"), this.appMessage.getString("message.salvoComSucesso"));
			FacesContext.getCurrentInstance().addMessage(null, fm);
			this.canticoBean.getCantico().setConsolador(this.consolador);
			this.canticoBean.setSelecionadoConsoladorCadastrado(true);
			this.visualizando();
		} catch (Exception ex) {
			ex.printStackTrace();
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, this.appMessage.getString("message.erroAoSalvarRegistro"));
			FacesContext.getCurrentInstance().addMessage(null, fm);
		}
	}

	public void setCanticoBean(CanticoBean canticoBean) {
		this.canticoBean = canticoBean;
	}
}
