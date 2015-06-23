package org.hinario.negocio.notificacao;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.activation.DataSource;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.hinario.app.AppConfig;
import org.hinario.app.AppMessage;
import org.hinario.dao.CanticoDAO;
import org.hinario.dao.UsuarioDAO;
import org.hinario.model.Arquivo;
import org.hinario.model.NotificacaoCanticoEmail;
import org.hinario.model.Usuario;
import org.hinario.model.enums.Motivo;
import org.hinario.util.CriptografiaUtil;

public class NotificadorPorEmail implements Serializable {

	private static final long serialVersionUID = 1L;

	private final UsuarioDAO usuarioDao;
	private final CanticoDAO canticoDao;
	private final AppMessage appMessage;
	private final SimpleDateFormat sdf;
	private static NotificadorPorEmail instancia;

	private NotificadorPorEmail() {
		this.usuarioDao = new UsuarioDAO();
		this.canticoDao = new CanticoDAO();
		this.appMessage = new AppMessage();
		this.sdf = new SimpleDateFormat("dd/MM/yyyy");
	}

	static {
		instancia = new NotificadorPorEmail();
	}

	public static NotificadorPorEmail getInstancia() {
		return instancia;
	}

	public synchronized void notificar(NotificacaoCanticoEmail notificacaoCanticoEmail) throws EmailException {
		notificacaoCanticoEmail = (NotificacaoCanticoEmail) canticoDao.atualizar(notificacaoCanticoEmail);
		if (notificacaoCanticoEmail.getDataEnvio() == null) {
			List<Usuario> usuarios = this.usuarioDao.getLista();
			List<Arquivo> arquivos = notificacaoCanticoEmail.getCantico().getArquivos();

			boolean SSL = "SSL".equalsIgnoreCase(AppConfig.getInstancia().getValorConfiguracao("email.tipoSeguranca"));
			boolean STARTTLS = "STARTTLS".equalsIgnoreCase(AppConfig.getInstancia().getValorConfiguracao("email.tipoSeguranca"));

			HtmlEmail email = new HtmlEmail();
			email.setDebug("true".equals(AppConfig.getInstancia().getValorConfiguracao("email.debug")));
			if (SSL) {
				email.setSSLOnConnect(true);
				email.setSslSmtpPort(AppConfig.getInstancia().getValorConfiguracao("email.smtp.porta"));
			} else if (STARTTLS) {
				email.setStartTLSEnabled(true);
				email.setStartTLSRequired(true);
				email.setSmtpPort(Integer.parseInt(AppConfig.getInstancia().getValorConfiguracao("email.smpt.porta")));
			}
			email.setFrom(AppConfig.getInstancia().getValorConfiguracao("email"), AppConfig.getInstancia().getValorConfiguracao("email.nome"));
			email.setAuthentication(AppConfig.getInstancia().getValorConfiguracao("email"), new CriptografiaUtil("hdaedi").descriptografar(AppConfig.getInstancia().getValorConfiguracao("email.senha")));
			email.setHostName(AppConfig.getInstancia().getValorConfiguracao("email.smtp.host"));
			email.setSubject(getAssunto(notificacaoCanticoEmail));

			for (Usuario usuario : usuarios) {
				email.addTo(usuario.getEmail(), usuario.getIrmao().getNome());
			}
			for (Arquivo arquivo : arquivos) {
				email.attach(new DataSourceArquivoCanticoAnexo(arquivo), arquivo.getNome(), arquivo.getNome());
			}
			String cid = email.embed(new DataSource() {

				@Override
				public OutputStream getOutputStream() throws IOException {
					return null;
				}

				@Override
				public String getName() {
					return "Imagem";
				}

				@Override
				public InputStream getInputStream() throws IOException {
					return this.getClass().getResource("../../../../../../../resources/imagens/favicon2.gif").openStream();
				}

				@Override
				public String getContentType() {
					return "image/png";
				}
			}, "Imagem", "logoHinario");

			email.setHtmlMsg(getHtmlMensagem(notificacaoCanticoEmail, cid));
			System.out.println(email.getSocketConnectionTimeout());
			System.out.println(email.getSocketTimeout());
			email.send();
			notificacaoCanticoEmail.setDataEnvio(new Date());
			this.canticoDao.salvar(notificacaoCanticoEmail);
		}
	}

	private String getAssunto(final NotificacaoCanticoEmail notificacaoCanticoEmail) {
		if (notificacaoCanticoEmail.getMotivo().equals(Motivo.INSERCAO)) {
			return this.appMessage.getString("label.emailNovoCanticoAssunto", notificacaoCanticoEmail.getCantico().getConsolador().getIrmao().getNome());
		} else if (notificacaoCanticoEmail.getMotivo().equals(Motivo.EDICAO)) {
			return this.appMessage.getString("label.emailAlteracaoCanticoAssunto", notificacaoCanticoEmail.getCantico().getConsolador().getIrmao().getNome());
		}
		return null;
	}

	private String getHtmlMensagem(final NotificacaoCanticoEmail notificacaoCanticoEmail, final String cid) {
		String chaveHtmlMensagem;

		if (notificacaoCanticoEmail.getMotivo().equals(Motivo.INSERCAO)) {
			chaveHtmlMensagem = "label.emailNovoCanticoMensagem";
		} else {
			chaveHtmlMensagem = "label.emailAlteracaoMensagem";
		}

		long id = notificacaoCanticoEmail.getCantico().getId();
		String consolador = notificacaoCanticoEmail.getCantico().getConsolador().getIrmao().getNome();
		String recebedor = notificacaoCanticoEmail.getCantico().getRecebedor() != null ? notificacaoCanticoEmail.getCantico().getRecebedor().getIrmao().getNome() : this.appMessage.getString("label.naoInformado");
		String dataRecebimento = notificacaoCanticoEmail.getCantico().getDataRecebimento() != null ? this.sdf.format(notificacaoCanticoEmail.getCantico().getDataRecebimento()) : this.appMessage.getString("label.naoInformado");
		String dataInsercaoOuAlteracao = this.sdf.format(notificacaoCanticoEmail.getDataAlteracao());

		String htmlMessagem = this.appMessage.getString(chaveHtmlMensagem, id, consolador, recebedor, dataRecebimento, dataInsercaoOuAlteracao);
		htmlMessagem.replace("logoHinario", cid);

		return htmlMessagem;
	}
}
