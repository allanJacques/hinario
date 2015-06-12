package org.hinario.negocio.managedbean;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class RecebedorDialogBean extends RecebedorBean {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{canticoBean}")
	private CanticoBean canticoBean;

	@Override
	public void salvar() {
		try {
			this.recebedor.getIrmao().setDataCadastro(new Date());
			dao.salvar(this.recebedor);
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, this.appMessage.getString("message.sucesso"), this.appMessage.getString("message.salvoComSucesso"));
			FacesContext.getCurrentInstance().addMessage(null, fm);
			this.canticoBean.getCantico().setRecebedor(this.recebedor);
			this.canticoBean.setSelecionadoRecebedorCadastrado(true);
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
