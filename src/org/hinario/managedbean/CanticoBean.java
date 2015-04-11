package org.hinario.managedbean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hinario.dao.CanticoDAO;
import org.hinario.dao.filtro.Filtro;
import org.hinario.model.Cantico;
import org.hinario.model.EntidadeBase;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.TabChangeEvent;

@ManagedBean
@ViewScoped
public class CanticoBean extends ManagedBeanBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private final CanticoDAO dao;
	private Cantico cantico;

	public CanticoBean() {
		this.dao = new CanticoDAO();
		this.setCantico(new Cantico());
		this.dataModel = new EntidadeDataModel(this, dao);
		this.filtro = new Filtro(Cantico.class);
	}

	public void salvar() {
		this.dao.salvar(this.getCantico());
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, this.appMessage.getString("message.sucesso"), this.appMessage.getString("message.salvoComSucesso"));
		FacesContext.getCurrentInstance().addMessage(null, fm);
		novo();
	}

	public void novo() {
		this.setCantico(new Cantico());
	}

	public void remover(final Cantico cantico) {
		this.dao.remover(cantico);
		this.novo();
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, this.appMessage.getString("message.sucesso"), this.appMessage.getString("message.removidoComSucesso"));
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}

	public void setTabAtiva(final Integer integer) {

	}

	public Integer getTabAtiva() {
		return 1;
	}

	public void onTabChange(TabChangeEvent event) {
		System.out.println(event.getTab().getTitle());
	}

	public String trocaAba(final FlowEvent event) {
		return event.getNewStep();
	}

	public Cantico getCantico() {
		return cantico;
	}

	public void setCantico(Cantico cantico) {
		this.cantico = cantico;
		super.setModoEditor();
	}

	@Override
	public void setEntidade(EntidadeBase entidade) {
		this.setCantico((Cantico) entidade);
	}

	@Override
	public EntidadeBase getEntidade() {
		return this.getCantico();
	}

}
