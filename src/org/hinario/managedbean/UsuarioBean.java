package org.hinario.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.hinario.app.AppMessage;
import org.hinario.app.ModoEditor;
import org.hinario.dao.UsuarioDAO;
import org.hinario.model.Usuario;
import org.hinario.util.CriptografiaUtil;
import org.hinario.util.IOUtil;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

@ManagedBean
@ViewScoped
public class UsuarioBean extends ManagedBeanBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private UsuarioDAO dao;
	private Part imageFile;
	private UsuarioDataModel usuarioDataModel;
	private int linhaSelecionanda;

	public UsuarioBean() {
		System.out.println("-----------------------------------------Novo UsuarioBean-----------------------------------------");
		this.usuario = new Usuario();
		this.dao = new UsuarioDAO();
		this.appMessage = new AppMessage();
		this.usuarioDataModel = new UsuarioDataModel();
	}

	public void salvar() {
		if (this.isAdicao() && dao.emailJaExiste(this.usuario.getEmail())) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, this.appMessage.getString("label.atencao"), this.appMessage.getString("message.emailJaExiste", this.usuario.getEmail()));
			FacesContext.getCurrentInstance().addMessage(null, fm);
			return;
		} else if (this.isEdicao() && dao.emailJaExisteEmOutroUsuario(this.usuario.getEmail(), this.usuario.getId())) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, this.appMessage.getString("label.atencao"), this.appMessage.getString("message.emailJaExiste", this.usuario.getEmail()));
			FacesContext.getCurrentInstance().addMessage(null, fm);
			return;
		}
		if (!this.usuario.getSenha().equals(this.usuario.getConfirmeSenha())) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, this.appMessage.getString("label.atencao"), this.appMessage.getString("message.senhasNaoConferem", this.usuario.getEmail()));
			FacesContext.getCurrentInstance().addMessage("senha", fm);
			FacesContext.getCurrentInstance().addMessage("confirmeSenha", fm);
			return;
		}
		this.usuario.setDataCadastro(new Date());
		if (isAdicao())
			this.usuario.setSenha(new CriptografiaUtil().criptografar(this.usuario.getSenha()));
		this.dao.salvar(this.usuario);
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, this.appMessage.getString("message.sucesso"), this.appMessage.getString("message.salvoComSucesso"));
		FacesContext.getCurrentInstance().addMessage(null, fm);
		this.adicionando();
		this.usuario = new Usuario();
	}

	public void remover(Usuario usuario) {
		this.dao.remover(usuario);
		if (usuario.getId().equals(this.usuario.getId())) {
			this.novo();
		}
	}

	public void novo() {
		this.usuario = new Usuario();
		this.adicionando();
	}

	public List<Usuario> getListaUsuario() {
		return dao.getListaUsuario();
	}

	public Part getImageFile() {
		return imageFile;
	}

	public void setImageFile(Part imageFile) {
		if (imageFile != null) {
			this.imageFile = imageFile;
			try {
				this.usuario.setImagem(new IOUtil().InputStreamToArray(this.imageFile.getInputStream(), this.imageFile.getSize()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		if (usuario != null && usuario.getIrmao().getNome() != null)
			this.usuario = usuario;
	}

	public UsuarioDataModel getUsuarioDataModel() {
		return usuarioDataModel;
	}

	public void setUsuarioDataModel(UsuarioDataModel usuarioDataModel) {
		this.usuarioDataModel = usuarioDataModel;
	}

	public int getLinhaSelecionanda() {
		return linhaSelecionanda;
	}

	public void setLinhaSelecionanda(int linhaSelecionanda) {
		this.linhaSelecionanda = linhaSelecionanda;
	}

	public class UsuarioDataModel extends LazyDataModel<Usuario> {

		private static final long serialVersionUID = 1L;

		@Override
		public List<Usuario> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
			setRowCount(dao.count().intValue());
			List<Usuario> returN = dao.getListaUsuario(first, pageSize);
			if (returN != null && !returN.isEmpty())
				UsuarioBean.this.usuario = returN.get(0);
			return returN;
		}

		@Override
		public List<Usuario> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
			setRowCount(dao.count().intValue());
			List<Usuario> returN = dao.getListaUsuario(first, pageSize);
			if (returN != null && !returN.isEmpty())
				UsuarioBean.this.usuario = returN.get(0);
			return returN;
		}

		@Override
		public Usuario getRowData(String rowKey) {
			try {
				usuario = dao.getUsuarioPorId(Long.parseLong(rowKey));
				usuario.setConfirmeSenha(usuario.getSenha());
				setModoEditor(ModoEditor.EDICAO);
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			return usuario;
		}

		@Override
		public Object getRowKey(Usuario usuario) {
			return usuario.getId();
		}

	}

}
