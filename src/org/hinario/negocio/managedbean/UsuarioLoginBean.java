package org.hinario.negocio.managedbean;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.hinario.dao.UsuarioDAO;
import org.hinario.model.EntidadeBase;
import org.hinario.model.Irmao;
import org.hinario.model.Usuario;
import org.hinario.util.CriptografiaSHA1Util;
import org.primefaces.component.tabview.Tab;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class UsuarioLoginBean extends ManagedBeanBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private String email;
	// variavel usada na hora de logar e na hora de trocar de senha como senha
	// atual
	private String senha;
	private Usuario usuario;
	private final UsuarioDAO dao;
	private Tab tab;
	private String novaSenha;
	private String confirmeNovaSenha;

	public UsuarioLoginBean() {
		this.dao = new UsuarioDAO();
	}

	public boolean isLogado() {
		return this.usuario != null;
	}

	public String login() {
		if (!this.dao.emailJaExiste(this.email)) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", this.appMessage.getString("message.emailNaoCadastrado")));
		} else {
			this.usuario = this.dao.valida(this.email, new CriptografiaSHA1Util().criptografar(this.senha));
			// this.usuario = this.usuarioDAO.valida(this.email, this.senha);
			this.senha = null;
			if (this.usuario == null) {
				FacesContext fc = FacesContext.getCurrentInstance();
				fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", this.appMessage.getString("message.senhaIncorreta")));
			} else {
				return "home?faces-redirect=true";
			}
		}
		return null;
	}

	public String logout() {
		((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().invalidate();
		return "login?faces-redirect=true";
	}

	public void salvarConfiguracoesPessoais() {
		this.usuario.setConfirmeSenha(this.usuario.getSenha());
		if (this.tab == null || this.tab.getTitle().equals(this.appMessage.getString("label.informacoesGerais"))) {
			if (dao.emailJaExisteEmOutroUsuario(this.getUsuario().getEmail(), this.getUsuario().getId())) {
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, this.appMessage.getString("label.atencao"), this.appMessage.getString("message.emailJaExiste", this.getUsuario().getEmail()));
				FacesContext.getCurrentInstance().addMessage(null, fm);
				this.setUsuario((Usuario) dao.atualizar(this.getUsuario()));
				return;
			} else if (dao.nomeJaExisteEmOutroIrmao(this.getUsuario().getIrmao().getNome(), this.getUsuario().getIrmao().getId())) {
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, this.appMessage.getString("label.atencao"), this.appMessage.getString("message.nomeJaExiste", this.getUsuario().getIrmao().getNome()));
				FacesContext.getCurrentInstance().addMessage(null, fm);
				this.usuario.setIrmao((Irmao) this.dao.atualizar(this.getUsuario().getIrmao()));
				return;
			} else {
				this.dao.salvar(this.getUsuario());
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, this.appMessage.getString("message.sucesso"), this.appMessage.getString("message.salvoComSucesso"));
				FacesContext.getCurrentInstance().addMessage(null, fm);
			}
		} else {
		}
	}

	public void trocarSenha() {
		if (this.usuario.getSenha().equals(new CriptografiaSHA1Util().criptografar(this.senha))) {
			if (this.novaSenha.equals(this.confirmeNovaSenha)) {
				this.usuario.setSenha(new CriptografiaSHA1Util().criptografar(this.novaSenha));
				this.dao.salvar(this.usuario);
				this.senha = null;
				this.novaSenha = null;
				this.confirmeNovaSenha = null;
				FacesContext fc = FacesContext.getCurrentInstance();
				fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", this.appMessage.getString("message.senhaAlterada")));
			} else {
				this.novaSenha = null;
				this.confirmeNovaSenha = null;
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, this.appMessage.getString("label.atencao"), this.appMessage.getString("message.senhasNaoConferem", this.getUsuario().getEmail()));
				FacesContext.getCurrentInstance().addMessage(null, fm);
			}
		} else {
			this.senha = null;
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", this.appMessage.getString("message.senhaIncorreta")));
		}
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

	@Override
	public void setEntidade(EntidadeBase entidade) {
		this.usuario = (Usuario) entidade;
	}

	@Override
	public EntidadeBase getEntidade() {
		return this.usuario;
	}

	public void mudarTab(TabChangeEvent evt) {
		this.tab = evt.getTab();
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmeNovaSenha() {
		return confirmeNovaSenha;
	}

	public void setConfirmeNovaSenha(String confirmeNovaSenha) {
		this.confirmeNovaSenha = confirmeNovaSenha;
	}

	public StreamedContent getImageUsuario() {
		return new DefaultStreamedContent(new ByteArrayInputStream(this.usuario.getImagem()));
	}

}
