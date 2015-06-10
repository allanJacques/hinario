package org.hinario.negocio.managedbean.notificacao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

import org.hinario.model.Arquivo;

public class DataSourceArquivoCanticoAnexo implements DataSource {

	public Arquivo arquivo;
	private InputStream in;

	public DataSourceArquivoCanticoAnexo(final Arquivo arquivo) {
		this.arquivo = arquivo;
		this.in = new ByteArrayInputStream(this.arquivo.getConteudo());
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return null;
	}

	@Override
	public String getName() {
		return this.arquivo.getNome();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return this.in;
	}

	@Override
	public String getContentType() {
		return this.arquivo.getMimeType();
	}

}
