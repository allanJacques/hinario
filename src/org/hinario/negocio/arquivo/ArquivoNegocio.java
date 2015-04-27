package org.hinario.negocio.arquivo;

import java.text.DecimalFormat;

public class ArquivoNegocio {
	private long kilo = 1024;
	private long mega = kilo * 1024;
	private long giga = mega * 1024;
	private DecimalFormat df = new DecimalFormat("#,##0.0");

	public ArquivoNegocio() {
	}

	public boolean isArquivoValido(final String mimeType) {
		for (MimeTypeArquivo mta : MimeTypeArquivo.values()) {
			if (mta.mimeType.equals(mimeType))
				return true;
		}
		return false;
	}

	public String tamanhoFormatado(final long tamanho) {
		String returN = null;
		double tamanhoDouble = tamanho;

		if (tamanho > giga) {
			returN = this.df.format(tamanhoDouble / giga) + " GB";
		} else if (tamanho > mega) {
			returN = this.df.format(tamanhoDouble / mega) + " MB";
		} else if (tamanho > kilo) {
			returN = this.df.format(tamanhoDouble / kilo) + " KB";
		} else {
			returN = this.df.format(tamanhoDouble) + " bytes";
		}

		return returN;
	}

	public MimeTypeArquivo getPorMimeType(final String mimeType) {
		for (MimeTypeArquivo mimeTypeTemp : MimeTypeArquivo.values()) {
			if (mimeTypeTemp.mimeType.equals(mimeType))
				return mimeTypeTemp;
		}
		return null;
	}

}
