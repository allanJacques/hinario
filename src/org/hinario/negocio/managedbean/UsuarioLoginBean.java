package org.hinario.negocio.managedbean;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionListener;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.hinario.dao.UsuarioDAO;
import org.hinario.model.EntidadeBase;
import org.hinario.model.Irmao;
import org.hinario.model.Usuario;
import org.hinario.util.CriptografiaSHA1Util;
import org.primefaces.component.tabview.Tab;
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.CroppedImage;
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
	private CroppedImage imagemRecortada;
	private final File path;
	private File imagem;
	private String novaSenha;
	private String confirmeNovaSenha;
	private StreamedContent fotoTemporariaStream;

	public UsuarioLoginBean() {
		this.dao = new UsuarioDAO();
		this.path = new File(new File(new File(this.getClass().getResource("UsuarioLoginBean.class").getPath()).getParentFile().getParentFile().getParentFile().getParentFile().getParentFile().getParentFile().getParentFile(), "resources"), "logins");
		if (!path.exists()) {
			this.path.mkdirs();
		}
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
			this.senha = null;
			if (this.usuario == null) {
				FacesContext fc = FacesContext.getCurrentInstance();
				fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", this.appMessage.getString("message.senhaIncorreta")));
			} else {
				this.salvarArquivoDeImagemLocal(null);
				return "home?faces-redirect=true";
			}
		}
		return null;
	}

	public void salvarArquivoDeImagemLocal(ActionListener event) {
		if (this.usuario.getImagem() != null)
			try {
				this.imagem = new File(this.path, this.usuario.getIrmao().getNome());
				FileOutputStream saida = new FileOutputStream(imagem);
				saida.write(this.usuario.getImagem());
				saida.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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

	public void mudarTab(TabChangeEvent evento) {
		this.tab = evento.getTab();
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
		if (this.usuario.getImagem() != null) {
			return new DefaultStreamedContent(new ByteArrayInputStream(this.usuario.getImagem()));
		} else {
			return null;
		}
	}

	public CroppedImage getImagemRecortada() {
		return imagemRecortada;
	}

	public void setImagemRecortada(CroppedImage imagemRecortada) {
		this.imagemRecortada = imagemRecortada;
	}

	public String cortar() {
		try {
			BufferedImage bufImgOrg = ImageIO.read(this.imagem);
			BufferedImage bufImgRec = bufImgOrg.getSubimage(this.imagemRecortada.getLeft(), this.imagemRecortada.getTop(), this.imagemRecortada.getWidth(), this.imagemRecortada.getHeight());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufImgRec, "png", baos);
			baos.toByteArray();
			this.usuario = (Usuario) this.dao.atualizar(this.usuario);
			this.imagemRecortada = null;
			this.usuario.setImagem(baos.toByteArray());
			baos.close();
			this.dao.salvar(this.usuario);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, this.appMessage.getString("label.erro"), this.appMessage.getString("message.erroAoCortarImage", e.getMessage())));
		}
		return "configuracoesPessoais";
	}

	public String novaImagemUpload(final FileUploadEvent evento) {
		try {
			this.usuario = (Usuario) this.dao.atualizar(this.usuario);
			this.usuario.setImagem(new byte[(int) evento.getFile().getSize()]);
			evento.getFile().getInputstream().read(this.usuario.getImagem());
			this.salvarArquivoDeImagemLocal(null);
			this.dao.salvar(this.usuario);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "configuracoesPessoais";
	}

	public void tirarFoto(CaptureEvent captureEvent) {
		UsuarioLoginBean.this.getUsuario().setImagem(captureEvent.getData());
		this.fotoTemporariaStream = new StreamedContent() {

			@Override
			public String getName() {
				return UsuarioLoginBean.this.getUsuario().getId() + System.currentTimeMillis() + ".jpeg";
			}

			@Override
			public InputStream getStream() {
				return new ByteArrayInputStream(UsuarioLoginBean.this.getUsuario().getImagem());
			}

			@Override
			public String getContentType() {
				return "image/jpeg";
			}

			@Override
			public String getContentEncoding() {
				return null;
			}

			@Override
			public String toString() {
				return this.getName();
			}
		};
	}

	public StreamedContent getFotoTemporaria() {
		if (this.fotoTemporariaStream != null) {
			return this.fotoTemporariaStream;
		} else {
			return new DefaultStreamedContent(new ByteArrayInputStream(this.usuario.getImagem()));
		}
	}

	public void alteraPraFotoTirada(final CloseEvent evento) {
		this.dao.salvar(this.usuario);
		this.salvarArquivoDeImagemLocal(null);
	}
}
