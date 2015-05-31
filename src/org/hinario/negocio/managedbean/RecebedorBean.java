package org.hinario.negocio.managedbean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hinario.dao.RecebedorDAO;
import org.hinario.dao.filtro.Filtro;
import org.hinario.model.EntidadeBase;
import org.hinario.model.Recebedor;
import org.hinario.model.enums.Sexo;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class RecebedorBean extends ManagedBeanBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private Recebedor recebedor;
	private final RecebedorDAO dao;

	public RecebedorBean() {
		this.setEntidade(new Recebedor());
		this.dao = new RecebedorDAO();
		this.dataModel = new EntidadeDataModel(this, this.dao);
		this.filtro = new Filtro(Recebedor.class);
	}

	public void novo() {
		this.setRecebedor(new Recebedor());
	}

	public void salvar() {
		try {
			this.recebedor.getIrmao().setDataCadastro(new Date());
			dao.salvar(this.recebedor);
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, this.appMessage.getString("message.sucesso"), this.appMessage.getString("message.salvoComSucesso"));
			FacesContext.getCurrentInstance().addMessage(null, fm);
			novo();
		} catch (Exception ex) {
			ex.printStackTrace();
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, this.appMessage.getString("message.erroAoSalvarRegistro"));
			FacesContext.getCurrentInstance().addMessage(null, fm);
		}
	}

	public List<String> listaSugestoesString(String valor) {
		return this.dao.listaNomeIrmaos(valor);
	}

	public List<Recebedor> listaSugestoesBean(String valor) {
		return this.dao.listaRecebedores(valor);
	}

	public void selecionou(SelectEvent event) {
		this.getRecebedor().setIrmao(this.dao.getIrmaoPorNome(event.getObject().toString()));
	}

	public void remover(final Recebedor recebedor) {
		dao.remover(recebedor);
		novo();
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, this.appMessage.getString("message.sucesso"), this.appMessage.getString("message.removidoComSucesso"));
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}

	@Override
	public void setEntidade(EntidadeBase entidade) {
		this.setRecebedor((Recebedor) entidade);
	}

	@Override
	public EntidadeBase getEntidade() {
		return this.getRecebedor();
	}

	public Recebedor getRecebedor() {
		return recebedor;
	}

	public void setRecebedor(Recebedor recebedor) {
		this.recebedor = recebedor;
		super.setModoEditor();
	}

	public Sexo[] getSexos() {
		return Sexo.values();
	}

	public EntidadeDataModel getUsuarioDataModel() {
		return dataModel;
	}

}
