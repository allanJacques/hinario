package org.hinario.managedbean;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hinario.dao.CanticoDAO;
import org.hinario.dao.OcasiaoDAO;
import org.hinario.dao.filtro.Filtro;
import org.hinario.model.Cantico;
import org.hinario.model.EntidadeBase;
import org.hinario.model.Ocasiao;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DualListModel;

@ManagedBean
@ViewScoped
public class CanticoBean extends ManagedBeanBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private final CanticoDAO dao;
	private final OcasiaoDAO daoOcasiao;
	private Cantico cantico;
	private DualListModel<Ocasiao> dualOcasioes;

	public CanticoBean() {
		this.dao = new CanticoDAO();
		this.daoOcasiao = new OcasiaoDAO();
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

	public String trocaAba(final FlowEvent event) {
		if (event.getNewStep().equals("tabOcasioes")) {
			this.carregaOcasioes();
		}
		if (event.getOldStep().equals("tabOcasioes")) {
			this.setaOcasioes();
		}
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

	public void setDualOcasioes(DualListModel<Ocasiao> dualOcasioes) {
		this.dualOcasioes = dualOcasioes;
	}

	public DualListModel<Ocasiao> getDualOcasioes() {
		return dualOcasioes;
	}

	@SuppressWarnings(value = { "unchecked" })
	private void carregaOcasioes() {
		List<Ocasiao> todasOcasioes = (List<Ocasiao>) this.daoOcasiao.getLista(null, null, null, null);
		for (Iterator<Ocasiao> iterator = todasOcasioes.iterator(); iterator.hasNext();) {
			if (this.cantico.getOcasioes().contains(iterator.next()))
				iterator.remove();
		}
		this.dualOcasioes = new DualListModel<Ocasiao>(todasOcasioes, this.cantico.getOcasioes());
	}

	private void setaOcasioes() {
		this.cantico.setOcasioes(this.dualOcasioes.getTarget());
	}

}
