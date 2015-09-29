package org.hinario.negocio.managedbean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hinario.model.Arquivo;
import org.hinario.model.EntidadeBase;

@ManagedBean
@ViewScoped
public class ArquivoBean extends ManagedBeanBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private Arquivo arquivo;

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	@Override
	public void setEntidade(EntidadeBase entidade) {
		this.setArquivo((Arquivo) entidade);
	}

	@Override
	public EntidadeBase getEntidade() {
		return this.getArquivo();
	}

	public boolean isArquivoVisualizavelEmBrowser(final Arquivo arquivo) {
		return ("image/jpeg".equals(arquivo.getMimeType()) || "image/png".equals(arquivo.getMimeType()) || "video/mp4".equals(arquivo.getMimeType()) || "audio/mpeg".equals(arquivo.getMimeType()) || "audio/mp3".equals(arquivo.getMimeType()) || "application/pdf".equals(arquivo.getMimeType()));
	}

}
