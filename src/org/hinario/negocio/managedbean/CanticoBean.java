package org.hinario.negocio.managedbean;

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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.hinario.dao.CanticoDAO;
import org.hinario.dao.OcasiaoDAO;
import org.hinario.dao.filtro.Filtro;
import org.hinario.model.Arquivo;
import org.hinario.model.Cantico;
import org.hinario.model.EntidadeBase;
import org.hinario.model.Ocasiao;
import org.hinario.model.enums.Motivo;
import org.hinario.negocio.arquivo.ArquivoNegocio;
import org.hinario.negocio.arquivo.MimeTypeArquivo;
import org.hinario.negocio.arquivo.TipoArquivo;
import org.hinario.negocio.notificacao.NotificacaoPorEmailTask;
import org.hinario.util.IOUtil;
import org.primefaces.event.CloseEvent;
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
	private final ArquivoNegocio arquivoNegocio;
	private final CanticoDAO dao;
	private final OcasiaoDAO daoOcasiao;
	private Cantico cantico;
	private DualListModel<Ocasiao> dualOcasioes;
	private boolean estaEmConfirmacao;
	private String step;
	private boolean selecionadoConsoladorCadastrado = false;
	private boolean selecionadoRecebedorCadastrado = false;

	public CanticoBean() {
		this.dao = new CanticoDAO();
		this.daoOcasiao = new OcasiaoDAO();
		this.novo();
		this.dataModel = new EntidadeDataModel(this, dao);
		this.filtro = new Filtro(Cantico.class);
		this.arquivoNegocio = new ArquivoNegocio();
	}

	public String salvar() {
		if (this.isAdicao()) {
			this.cantico.setDataCadastro(new Date());
		}
		ArrayList<Arquivo> arquivos = new ArrayList<>();
		arquivos.addAll(this.cantico.getArquivos());
		this.cantico.setArquivos(arquivos);
		this.dao.salvar(this.getCantico());
		this.notificar(this.getCantico(), this.isAdicao() ? Motivo.INSERCAO : Motivo.EDICAO);
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, this.appMessage.getString("message.sucesso"), this.appMessage.getString("message.salvoComSucesso"));
		FacesContext.getCurrentInstance().addMessage(null, fm);
		novo();
		// Adicionando no FlashScoped, porque esse botao salvar redireciona para
		// a mesma pagina
		FacesContext instance = FacesContext.getCurrentInstance();
		ExternalContext externalContext = instance.getExternalContext();
		externalContext.getFlash().put("salvoComSucesso", this.appMessage.getString("message.salvoComSucesso"));
		externalContext.getFlash().setKeepMessages(true);
		return "ok";
	}

	private void notificar(Cantico cantico, Motivo motivo) {
		NotificacaoPorEmailTask t = new NotificacaoPorEmailTask(cantico, motivo);
		t.setPriority(Thread.MIN_PRIORITY);
		t.start();
	}

	public void novo() {
		this.setCantico(new Cantico());
		this.step = "tabConsoladorRecebedor";
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
					arquivoTemp.setMimeType(event.getFile().getContentType());
					arquivoTemp.setTamanho(event.getFile().getSize());
					arquivoTemp.setNome(event.getFile().getFileName());
					arquivoTemp.setCantico(this.cantico);
					this.cantico.getArquivos().add(arquivoTemp);
					FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, this.appMessage.getString("label.arquivoRecebido"), this.appMessage.getString("message.arquivoRecebido", event.getFile().getFileName()));
					FacesContext.getCurrentInstance().addMessage(null, fm);
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
		return this.arquivoNegocio.isArquivoValido(file.getContentType());
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
		if (this.cantico != null && this.cantico.getId() != null) {
			this.step = "tabConfirmacao";
		}
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
	public void carregaOcasioes() {
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

	public ArquivoNegocio getArquivoNegocio() {
		return arquivoNegocio;
	}

	public String tamanhoFormatado(final long tamanho) {
		return this.arquivoNegocio.tamanhoFormatado(tamanho);
	}

	public boolean isCanticoValido() {
		return this.estaEmConfirmacao && temDocumento();
	}

	public boolean temAudio() {
		return temArquivo(TipoArquivo.AUDIO);
	}

	public boolean temRecebedor() {
		return (this.cantico != null && this.cantico.getRecebedor() != null);
	}

	public boolean temDataRecebimento() {
		return (this.cantico != null && this.cantico.getDataRecebimento() != null);
	}

	public boolean temDocumento() {
		return temArquivo(TipoArquivo.DOCUMENTO);
	}

	private boolean temArquivo(final TipoArquivo tipoArquivo) {
		if (this.cantico != null && this.cantico.getArquivos() != null && !this.cantico.getArquivos().isEmpty())
			for (Arquivo arquivoTemp : this.cantico.getArquivos()) {
				if (tipoArquivo.equals(this.arquivoNegocio.getPorMimeType(arquivoTemp.getMimeType()).tipoArquivo)) {
					return true;
				}
			}
		return false;
	}

	public void removerArquivo(final Arquivo arquivo) {
		this.cantico.getArquivos().remove(arquivo);
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, this.appMessage.getString("label.arquivoExcluido"), this.appMessage.getString("message.arquivoExcluido", arquivo.getNome()));
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getResumoArquivosString(final Cantico cantico) {
		byte arquivosAudio = 0;
		byte arquivosDocumento = 0;
		byte arquivosImagem = 0;
		byte arquivosVideo = 0;
		StringBuilder returN = new StringBuilder();
		for (final Arquivo arqTem : cantico.getArquivos()) {
			TipoArquivo tipo = MimeTypeArquivo.getTipoArquivo(arqTem.getMimeType());

			if (TipoArquivo.AUDIO.equals(tipo)) {
				arquivosAudio++;
			} else if (TipoArquivo.DOCUMENTO.equals(tipo)) {
				arquivosDocumento++;
			} else if (TipoArquivo.IMAGEM.equals(tipo)) {
				arquivosImagem++;
			} else if (TipoArquivo.VIDEO.equals(tipo)) {
				arquivosVideo++;
			}
		}
		final String separador = ", ";
		final String separadorFinal = this.appMessage.getString("label.ultimoSeparador");
		final String espaco = " ";
		if (arquivosAudio > 0) {
			returN.append(arquivosAudio);
			returN.append(espaco);
			returN.append(this.appMessage.getString((arquivosAudio > 1) ? "label.arquivosDeAudio" : "label.arquivoDeAudio"));
			returN.append(separador);
		}
		if (arquivosDocumento > 0) {
			returN.append(arquivosDocumento);
			returN.append(espaco);
			returN.append(this.appMessage.getString((arquivosDocumento > 1) ? "label.arquivosDeDocumento" : "label.arquivoDeDocumento"));
			returN.append(separador);
		}
		if (arquivosImagem > 0) {
			returN.append(arquivosImagem);
			returN.append(espaco);
			returN.append(this.appMessage.getString((arquivosImagem > 1) ? "label.arquivosDeImagem" : "label.arquivoDeImagem"));
			returN.append(separador);
		}
		if (arquivosVideo > 0) {
			returN.append(arquivosVideo);
			returN.append(espaco);
			returN.append(this.appMessage.getString((arquivosVideo > 1) ? "label.arquivosDeVideo" : "label.arquivoDeVideo"));
		}
		if (returN.toString().endsWith(", ")) {
			returN.delete(returN.length() - 2, returN.length());
		}
		final int ultimaVirgula = returN.lastIndexOf(",", returN.length());
		if (ultimaVirgula != -1) {
			returN.replace(ultimaVirgula, ultimaVirgula + 1, separadorFinal);
		}

		return returN.toString();
	}

	public String getResumoOcasioes(final Cantico cantico) {
		StringBuilder returN = new StringBuilder();

		for (Ocasiao ocasiaoTemp : cantico.getOcasioes()) {
			returN.append(ocasiaoTemp.getDescricao());
			returN.append(" ");
			returN.append(", ");
		}

		if (returN.toString().endsWith(", "))
			returN.delete(returN.length() - 2, returN.length());

		final int ultimaVirgula = returN.lastIndexOf(",", returN.length());
		if (ultimaVirgula != -1) {
			returN.replace(ultimaVirgula, ultimaVirgula + 1, this.appMessage.getString("label.ultimoSeparador"));
		}

		return returN.toString();
	}

	public void selecionaConsoladorCadastrado(final CloseEvent event) {
		this.selecionadoConsoladorCadastrado = this.cantico.getConsolador() != null && this.cantico.getConsolador().getId() != null;
	}

	public boolean isSelecionadoConsoladorCadastrado() {
		return selecionadoConsoladorCadastrado;
	}

	public void setSelecionadoConsoladorCadastrado(boolean selecionadoConsoladorCadastrado) {
		this.selecionadoConsoladorCadastrado = selecionadoConsoladorCadastrado;
	}

	public void limpaCampoConsoladorCadastrado() {
		this.cantico.setConsolador(null);
		this.selecionadoConsoladorCadastrado = false;
	}

	public void selecionaRecebedorCadastrado(final CloseEvent event) {
		this.selecionadoRecebedorCadastrado = this.cantico.getRecebedor() != null && this.cantico.getRecebedor().getId() != null;
	}

	public boolean isSelecionadoRecebedorCadastrado() {
		return selecionadoRecebedorCadastrado;
	}

	public void setSelecionadoRecebedorCadastrado(boolean selecionadoRecebedorCadastrado) {
		this.selecionadoRecebedorCadastrado = selecionadoRecebedorCadastrado;
	}

	public void limpaCampoRecebedorCadastrado() {
		this.cantico.setRecebedor(null);
		this.selecionadoRecebedorCadastrado = false;
	}

}
