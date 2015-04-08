package org.hinario.managedbean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hinario.dao.ConsoladorDAO;
import org.hinario.dao.filtro.Filtro;
import org.hinario.model.Consolador;
import org.hinario.model.EntidadeBase;
import org.hinario.model.enums.Sexo;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class ConsoladorBean extends ManagedBeanBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private Consolador consolador;
	private ConsoladorDAO dao;

	public ConsoladorBean() {
		this.setEntidade(new Consolador());
		this.dao = new ConsoladorDAO();
		this.usuarioDataModel = new EntidadeDataModel(this, this.dao);
		this.filtro = new Filtro(Consolador.class);
	}

	public void novo() {
		this.setConsolador(new Consolador());
	}

	public void salvar() {
		try {
			this.consolador.getIrmao().setDataCadastro(new Date());
			dao.salvar(this.consolador);
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, this.appMessage.getString("message.sucesso"), this.appMessage.getString("message.salvoComSucesso"));
			FacesContext.getCurrentInstance().addMessage(null, fm);
			novo();
		} catch (Exception ex) {
			ex.printStackTrace();
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, this.appMessage.getString("message.erroAoSalvarRegistro"));
			FacesContext.getCurrentInstance().addMessage(null, fm);
		}
	}

	public void remover(final Consolador consolador) {
		dao.remover(consolador);
		novo();
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, this.appMessage.getString("message.sucesso"), this.appMessage.getString("message.removidoComSucesso"));
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}

	public List<String> listaSugestoes(String valor) {
		return this.dao.listaNomeIrmaos(valor);
	}

	public void selecionou(SelectEvent event) {
		this.getConsolador().setIrmao(this.dao.getIrmaoPorNome(event.getObject().toString()));
	}

	@Override
	public void setEntidade(EntidadeBase entidade) {
		this.setConsolador((Consolador) entidade);
	}

	@Override
	public EntidadeBase getEntidade() {
		return this.getConsolador();
	}

	public Consolador getConsolador() {
		return consolador;
	}

	public void setConsolador(Consolador consolador) {
		this.consolador = consolador;
		super.setModoEditor();
	}

	public Sexo[] getSexos() {
		return Sexo.values();
	}

	public EntidadeDataModel getUsuarioDataModel() {
		return usuarioDataModel;
	}

}
