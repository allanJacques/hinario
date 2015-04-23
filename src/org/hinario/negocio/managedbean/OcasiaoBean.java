package org.hinario.negocio.managedbean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hinario.dao.OcasiaoDAO;
import org.hinario.dao.filtro.Filtro;
import org.hinario.model.EntidadeBase;
import org.hinario.model.Ocasiao;

@ManagedBean
@ViewScoped
public class OcasiaoBean extends ManagedBeanBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private Ocasiao ocasiao = null;
	private final OcasiaoDAO dao;

	public OcasiaoBean() {
		this.setEntidade(new Ocasiao());
		this.dao = new OcasiaoDAO();
		this.dataModel = new EntidadeDataModel(this, this.dao);
		this.filtro = new Filtro(Ocasiao.class);
	}

	public void salvar() {
		this.dao.salvar(this.getOcasiao());
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, this.appMessage.getString("message.sucesso"), this.appMessage.getString("message.salvoComSucesso"));
		FacesContext.getCurrentInstance().addMessage(null, fm);
		this.novo();
	}

	public void novo() {
		this.setOcasiao(new Ocasiao());
	}

	@Override
	public void setEntidade(EntidadeBase entidade) {
		this.setOcasiao((Ocasiao) entidade);
	}

	@Override
	public EntidadeBase getEntidade() {
		return this.getOcasiao();
	}

	public Ocasiao getOcasiao() {
		return ocasiao;
	}

	public void setOcasiao(Ocasiao ocasiao) {
		this.ocasiao = ocasiao;
		super.setModoEditor();
	}

	public EntidadeDataModel getOcasiaoDataModel() {
		return this.dataModel;
	}

	public void setOcasiaoDataModel(EntidadeDataModel ocasiaoDataModel) {
		this.dataModel = ocasiaoDataModel;
	}

	public void remover(Ocasiao ocasiao) {
		this.dao.remover(ocasiao);
		this.novo();
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, this.appMessage.getString("message.sucesso"), this.appMessage.getString("message.removidoComSucesso"));
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}

}
