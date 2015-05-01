package org.hinario.negocio.arquivo;

import org.hinario.app.AppMessage;

public enum MimeTypeArquivo {

	DOC("application/msword", TipoArquivo.DOCUMENTO, AppMessage.getStaticString("enum.mimeTypeArquivo.application-msword")), /**/
	DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document", TipoArquivo.DOCUMENTO, AppMessage.getStaticString("enum.mimeTypeArquivo.application-vnd.openxmlformats-officedocument.wordprocessingml.document")), /**/
	ODT("application/vnd.oasis.opendocument.text", TipoArquivo.DOCUMENTO, AppMessage.getStaticString("enum.mimeTypeArquivo.application-vnd.oasis.opendocument.text")), /**/
	PDF("application/pdf", TipoArquivo.DOCUMENTO, AppMessage.getStaticString("enum.mimeTypeArquivo.application-pdf")), /**/
	MP3("audio/mp3", TipoArquivo.AUDIO, AppMessage.getStaticString("enum.mimeTypeArquivo.audio-mp3")), /**/
	MPEG("audio/mpeg", TipoArquivo.AUDIO, AppMessage.getStaticString("enum.mimeTypeArquivo.audio-mpeg")), /**/
	MP4("video/mp4", TipoArquivo.VIDEO, AppMessage.getStaticString("enum.mimeTypeArquivo.video-mp4")), /**/
	PNG("image/png", TipoArquivo.IMAGEM, AppMessage.getStaticString("enum.mimeTypeArquivo.image-png")), /**/
	JPEG("image/jpeg", TipoArquivo.IMAGEM, AppMessage.getStaticString("enum.mimeTypeArquivo.image-jpeg"));/**/

	private MimeTypeArquivo(String mimeType, TipoArquivo tipoArquivo, String descricao) {
		this.mimeType = mimeType;
		this.tipoArquivo = tipoArquivo;
		this.descricao = descricao;
	}

	public String mimeType;
	public TipoArquivo tipoArquivo;
	public String descricao;

	public static TipoArquivo getTipoArquivo(final String mimeType) {
		for (final MimeTypeArquivo mimeTypeTemp : MimeTypeArquivo.values()) {
			if (mimeTypeTemp.mimeType.equals(mimeType))
				return mimeTypeTemp.tipoArquivo;
		}
		return null;
	}

}
