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
import javax.swing.JOptionPane;

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
public class UsuarioBean extends ManagedBeanBase<Usuario> implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuario = null;
	private UsuarioDAO dao;
	private Part imageFile;
	private UsuarioDataModel usuarioDataModel;
	private int linhaSelecionanda;

	public UsuarioBean() {
		System.out.println("-----------------------------------------Novo UsuarioBean-----------------------------------------");
		this.setUsuario(new Usuario());
		this.dao = new UsuarioDAO();
		this.appMessage = new AppMessage();
		this.usuarioDataModel = new UsuarioDataModel();
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
		this.getUsuario().setDataCadastro(new Date());
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

	public int ordena(final Object o) {
		System.out.println(o);
		JOptionPane.showMessageDialog(null, "asdfas");
		return 0;
	}

	public class UsuarioDataModel extends LazyDataModel<Usuario> {

		private static final long serialVersionUID = 1L;

		@Override
		public List<Usuario> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
			setRowCount(dao.count().intValue());
			for (String key : filters.keySet()) {
				System.out.println(key);
				System.out.println(filters.get(key));
				System.out.println(filters.get(key).getClass());
				System.out.println("====================");
			}
			List<Usuario> returN = dao.getListaUsuario(first, pageSize, multiSortMeta);
			return returN;
		}

		@Override
		public List<Usuario> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
			setRowCount(dao.count().intValue());
			List<Usuario> returN = dao.getListaUsuario(first, pageSize, null);
			return returN;
		}

		@Override
		public Usuario getRowData(String rowKey) {
			try {
				UsuarioBean.this.setUsuario(dao.getUsuarioPorId(Long.parseLong(rowKey)));
				UsuarioBean.this.getUsuario().setConfirmeSenha(UsuarioBean.this.getUsuario().getSenha());
				setModoEditor(ModoEditor.EDICAO);
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			return UsuarioBean.this.getUsuario();
		}

		@Override
		public Object getRowKey(Usuario usuario) {
			return usuario.getId();
		}

	}

}
