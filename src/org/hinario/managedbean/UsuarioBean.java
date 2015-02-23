package org.hinario.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.hinario.dao.UsuarioDAO;
import org.hinario.dao.filtro.Campo;
import org.hinario.dao.filtro.Filtro;
import org.hinario.model.EntidadeBase;
import org.hinario.model.Usuario;
import org.hinario.model.enums.Sexo;
import org.hinario.util.CriptografiaUtil;
import org.hinario.util.IOUtil;

@ManagedBean
@SessionScoped
public class UsuarioBean extends ManagedBeanBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuario = null;
	private UsuarioDAO dao;
	private Part imageFile;
	private Campo campo;

	public UsuarioBean() {
		System.out.println("----------------------------------------------------------------------------------Novo UsuarioBean----------------------------------------------------------------------------------");
		this.setUsuario(new Usuario());
		this.dao = new UsuarioDAO();
		this.usuarioDataModel = new EntidadeDataModel(this, this.dao);
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
		this.adicionando();
		this.setUsuario(new Usuario());
	}

	public void remover(Usuario usuario) {
		this.dao.remover(usuario);
		this.novo();
	}

	public void novo() {
		this.setUsuario(new Usuario());
		this.adicionando();
	}

	public Part getImageFile() {
		return imageFile;
	}

	public void setImageFile(Part imageFile) {
		if (imageFile != null) {
			this.imageFile = imageFile;
			try {
				this.getUsuario().setImagem(new IOUtil().InputStreamToArray(this.imageFile.getInputStream(), this.imageFile.getSize()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		System.out.println("Setando usuario com nome: " + usuario.getIrmao().getNome());
		this.usuario = usuario;
	}

	public EntidadeDataModel getUsuarioDataModel() {
		return usuarioDataModel;
	}

	public void setUsuarioDataModel(EntidadeDataModel usuarioDataModel) {
		this.usuarioDataModel = usuarioDataModel;
	}

	public Campo getCampo() {
		return campo;
	}

	public void setCampo(Campo campo) {
		this.campo = campo;
	}

	public Sexo[] getSexos() {
		return Sexo.values();
	}

	@Override
	public void setEntidade(EntidadeBase entidade) {
		this.usuario = (Usuario) entidade;
		this.getUsuario().setConfirmeSenha(this.getUsuario().getSenha());
	}

	@Override
	public EntidadeBase getEntidade() {
		return this.usuario;
	}

}
