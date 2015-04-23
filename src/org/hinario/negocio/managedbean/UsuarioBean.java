package org.hinario.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.hinario.dao.UsuarioDAO;
import org.hinario.dao.filtro.Filtro;
import org.hinario.model.EntidadeBase;
import org.hinario.model.Usuario;
import org.hinario.model.enums.Sexo;
import org.hinario.util.CriptografiaUtil;
import org.hinario.util.IOUtil;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class UsuarioBean extends ManagedBeanBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuario = null;
	private final UsuarioDAO dao;
	private Part imageFile;

	public UsuarioBean() {
		this.setUsuario(new Usuario());
		this.dao = new UsuarioDAO();
		this.dataModel = new EntidadeDataModel(this, this.dao);
		this.filtro = new Filtro(Usuario.class);
	}

	public void salvar() {
		if (this.isAdicao() && dao.emailJaExiste(this.getUsuario().getEmail())) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, this.appMessage.getString("label.atencao"), this.appMessage.getString("message.emailJaExiste", this.getUsuario().getEmail()));
			FacesContext.getCurrentInstance().addMessage(null, fm);
			return;
		} else if (this.isEdicao() && dao.emailJaExisteEmOutroUsuario(this.getUsuario().getEmail(), this.getUsuario().getId())) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, this.appMessage.getString("label.atencao"), this.appMessage.getString("message.emailJaExiste", this.getUsuario().getEmail()));
			FacesContext.getCurrentInstance().addMessage(null, fm);
			return;
		}
		if (!this.getUsuario().getSenha().equals(this.getUsuario().getConfirmeSenha())) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, this.appMessage.getString("label.atencao"), this.appMessage.getString("message.senhasNaoConferem", this.getUsuario().getEmail()));
			FacesContext.getCurrentInstance().addMessage("senha", fm);
			FacesContext.getCurrentInstance().addMessage("confirmeSenha", fm);
			return;
		}
		this.getUsuario().getIrmao().setDataCadastro(new Date());
		if (isAdicao())
			this.getUsuario().setSenha(new CriptografiaUtil().criptografar(this.getUsuario().getSenha()));
		this.dao.salvar(this.getUsuario());
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, this.appMessage.getString("message.sucesso"), this.appMessage.getString("message.salvoComSucesso"));
		FacesContext.getCurrentInstance().addMessage(null, fm);
		novo();
	}

	public void remover(Usuario usuario) {
		this.dao.remover(usuario);
		this.novo();
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, this.appMessage.getString("message.sucesso"), this.appMessage.getString("message.removidoComSucesso"));
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}

	public void novo() {
		this.setUsuario(new Usuario());
	}

	public List<String> listaSugestoes(String valor) {
		return this.dao.listaNomeIrmaos(valor);
	}

	public void selecionou(SelectEvent event) {
		this.getUsuario().setIrmao(this.dao.getIrmaoPorNome(event.getObject().toString()));
	}

	public Part getImageFile() {
		return imageFile;
	}

	public void setImageFile(Part imageFile) {
		if (imageFile != null) {
			this.imageFile = imageFile;
			try {
				this.getUsuario().setImagem(new IOUtil().InputStreamToByteArray(this.imageFile.getInputStream(), this.imageFile.getSize()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
		super.setModoEditor();
	}

	public Sexo[] getSexos() {
		return Sexo.values();
	}

	@Override
	public void setEntidade(EntidadeBase entidade) {
		this.setUsuario((Usuario) entidade);
		this.getUsuario().setConfirmeSenha(this.getUsuario().getSenha());
	}

	@Override
	public EntidadeBase getEntidade() {
		return this.getUsuario();
	}

}
