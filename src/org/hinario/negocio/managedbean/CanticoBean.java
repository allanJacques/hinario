package org.hinario.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hinario.dao.CanticoDAO;
import org.hinario.dao.OcasiaoDAO;
import org.hinario.dao.filtro.Filtro;
import org.hinario.model.Arquivo;
import org.hinario.model.Cantico;
import org.hinario.model.EntidadeBase;
import org.hinario.model.Ocasiao;
import org.hinario.util.IOUtil;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;

@ManagedBean
@ViewScoped
public class CanticoBean extends ManagedBeanBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private final byte maxArquivos = 127;
	private final long maxTamanhoArquivo = 10000 * 1024 * 1024; // 8Megabytes
	private final ArrayList<String> MIMETypesAceitos;
	private final CanticoDAO dao;
	private final OcasiaoDAO daoOcasiao;
	private Cantico cantico;
	private DualListModel<Ocasiao> dualOcasioes;
	private boolean estaEmConfirmacao;

	public CanticoBean() {
		this.dao = new CanticoDAO();
		this.daoOcasiao = new OcasiaoDAO();
		this.setCantico(new Cantico());
		this.dataModel = new EntidadeDataModel(this, dao);
		this.filtro = new Filtro(Cantico.class);
		this.MIMETypesAceitos = new ArrayList<>();
		this.addMIMETypesAceitos();
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

	public Date getDataCadastroCantico() {
		if (this.isEdicao() && this.cantico.getDataCadastro() != null) {
			return this.cantico.getDataCadastro();
		} else {
			return new Date();
		}

	}

	public String trocaAba(final FlowEvent event) {
		this.estaEmConfirmacao = "tabConfirmacao".equals(event.getNewStep());
		if ("tabOcasioes".equals(event.getNewStep())) {
			this.carregaOcasioes();
		}
		if ("tabOcasioes".equals(event.getOldStep())) {
			this.setaOcasioes();
		}
		return event.getNewStep();
	}

	public void upload(FileUploadEvent event) {
		try {
			if (this.cantico.getArquivos().size() < maxArquivos) {
				if (isArquivoValido(event.getFile())) {
					Arquivo arquivoTemp = new Arquivo();
					arquivoTemp.setConteudo(new IOUtil().InputStreamToByteArray(event.getFile().getInputstream(), event.getFile().getSize()));
					arquivoTemp.setDataUpload(new Date());
					arquivoTemp.setMIMEType(event.getFile().getContentType());
					arquivoTemp.setNome(event.getFile().getFileName());
					this.cantico.getArquivos().add(arquivoTemp);
				} else {
					Logger.getGlobal().log(Level.INFO, "Tipo nao aceito: " + event.getFile().getContentType());
					FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, this.appMessage.getString("label.arquivoInvalido"), this.appMessage.getString("message.fileUpload.tipoDeArquivoInvalido"));
					FacesContext.getCurrentInstance().addMessage(null, fm);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isArquivoValido(UploadedFile file) {
		return this.MIMETypesAceitos.contains(file.getContentType());
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

	public byte getMaxArquivos() {
		return maxArquivos;
	}

	public long getMaxTamanhoArquivo() {
		return maxTamanhoArquivo;
	}

	private void addMIMETypesAceitos() {
		/* Documentos editaveis */
		this.MIMETypesAceitos.add("application/msword"); // .doc
		this.MIMETypesAceitos.add("application/vnd.openxmlformats-officedocument.wordprocessingml.document"); // .docx
		this.MIMETypesAceitos.add("application/vnd.oasis.opendocument.text"); // odt

		/* Documentos nao editaveis */
		this.MIMETypesAceitos.add("application/pdf");

		/* Audio */
		this.MIMETypesAceitos.add("audio/mp3");

		/* Video */
		this.MIMETypesAceitos.add("video/mp4");

		/* Imagem, caso esteja escaneado */
		this.MIMETypesAceitos.add("image/png");
		this.MIMETypesAceitos.add("image/jpeg");

	}

	public boolean isCanticoValido() {
		return this.estaEmConfirmacao;
	}
}
