package org.hinario.managedbean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hinario.dao.UsuarioDAO;
import org.hinario.model.Usuario;
import org.hinario.util.CriptografiaUtil;

@ManagedBean
@SessionScoped
public class UsuarioLoginBean extends ManagedBeanBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private String email;
	private String senha;
	private Usuario usuario;
	private UsuarioDAO usuarioDAO;

	public UsuarioLoginBean() {
		this.usuarioDAO = new UsuarioDAO();
	}

	public boolean isLogado() {
		return this.usuario != null;
	}

	public String login() {
		if (!this.usuarioDAO.emailJaExiste(this.email)) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, this.appMessage.getString("")));
		} else {
			this.usuario = this.usuarioDAO.valida(this.email, new CriptografiaUtil().criptografar(this.senha));
			if (this.usuario == null) {
				FacesContext fc = FacesContext.getCurrentInstance();
				fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, this.appMessage.getString("")));
			} else {
				return "index?faces-redirect=true";
			}
		}
		return null;
	}

	public String logout() {
		return null;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
