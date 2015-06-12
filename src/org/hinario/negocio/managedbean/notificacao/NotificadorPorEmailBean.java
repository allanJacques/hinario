package org.hinario.negocio.managedbean.notificacao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.activation.DataSource;
import javax.annotation.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.hinario.app.AppMessage;
import org.hinario.dao.CanticoDAO;
import org.hinario.dao.UsuarioDAO;
import org.hinario.model.Arquivo;
import org.hinario.model.NotificacaoCanticoEmail;
import org.hinario.model.Usuario;
import org.hinario.model.enums.Motivo;

@ManagedBean()
@ApplicationScoped
public class NotificadorPorEmailBean {

	private final UsuarioDAO usuarioDao;
	private final CanticoDAO canticoDao;
	private final AppMessage appMessage;
	private final SimpleDateFormat sdf;

	public NotificadorPorEmailBean() {
		this.usuarioDao = new UsuarioDAO();
		this.canticoDao = new CanticoDAO();
		this.appMessage = new AppMessage();
<<<<<<< 38f1891dddcc7d027de46ef824f2c9e09543366a
	}

	public static void main(String[] args) throws EmailException, MalformedURLException {
		System.out.println("Fora Dilma");

		boolean SSL = !true;
		boolean STARTTLS = true;

		HtmlEmail email;

		email = new HtmlEmail();

		email.setDebug(true);

		email.setFrom("allanjnofs@gmail.com", "Allan Jacques");
		// email.setFrom("allan_jno@hotmail.com");

		email.setAuthentication("allanjnofs@gmail.com", "unirondon");
		// email.setAuthentication("allan_jno@hotmail.com", "bradesco192939.");

		email.setHostName("smtp.googlemail.com");
		// email.setHostName("smtp-mail.outlook.com");

		email.setSubject("Testando Apache Commons Mail " + System.currentTimeMillis());
		email.addTo("allanjnofs@gmail.com", "Allan Jacques GMail");
		email.addCc("allan_jno@hotmail.com", "Allan Jacques Hotmail");
		email.setMsg("Mensagem de Teste!");

		// para GMail com SSL
		if (SSL) {
			email.setSSLOnConnect(true);
			email.setSslSmtpPort("465");
		} else if (STARTTLS) {
			email.setStartTLSEnabled(true);
			email.setStartTLSRequired(true);
			email.setSmtpPort(587);
		}

		// email.attach(new File("/home/allan/apagar.png"));
		// email.attach(new
		// URL("http://www.teciber.com/wp-content/uploads/2015/05/3256999-4762864562-hulk_-640x400.jpg"),
		// "Hulk", "Imagem do Hulk");

		email.attach(new DataSource() {

			@Override
			public OutputStream getOutputStream() throws IOException {
				System.out.println("Chamado metodo: getOutputStream()");
				return null;

			}

			@Override
			public String getName() {
				System.out.println("Chamado metodo: getName()");
				return "Imagem Apagar";
			}

			@Override
			public InputStream getInputStream() throws IOException {
				System.out.println("Chamado metodo: getInputStream()");
				return new FileInputStream(new File("/home/allan/apagar.png"));
			}

			@Override
			public String getContentType() {
				System.out.println("Chamado metodo: getContentType()");
				return "image/png";
			}
		}, "Nome da Imagem Apagar", "Descrição da Imagem Apagar");

		String cid = email.embed(new DataSource() {

			@Override
			public OutputStream getOutputStream() throws IOException {
				System.out.println("Chamado metodo: getOutputStream()");
				return null;

			}

			@Override
			public String getName() {
				System.out.println("Chamado metodo: getName()");
				return "Imagem Apagar";
			}

			@Override
			public InputStream getInputStream() throws IOException {
				System.out.println("Chamado metodo: getInputStream()");
				return new FileInputStream(new File("/home/allan/apagar.png"));
			}

			@Override
			public String getContentType() {
				System.out.println("Chamado metodo: getContentType()");
				return "image/png";
			}
		}, "Imagem Embeded", "apagar");

		System.out.println("cid: " + cid);

		email.setHtmlMsg("<html><p>Parágrafo normal</p><img src=\"cid:" + cid + "\">asdf</img></html>");

		System.out.println("Enviando...");
		email.send();
		System.out.println("\n\n\nEnviado.");

=======
		this.sdf = new SimpleDateFormat("dd/MM/yyyy");
>>>>>>> c1230cefc90daa56006974161091efc033993abc
	}

	public void notificar(NotificacaoCanticoEmail notificacaoCanticoEmail) {

		}
<<<<<<< 38f1891dddcc7d027de46ef824f2c9e09543366a

=======
		String cid = email.embed(new DataSource() {

			@Override
			public OutputStream getOutputStream() throws IOException {
				return null;
			}

			@Override
			public String getName() {
				return "Imagem Apagar";
			}

			@Override
			public InputStream getInputStream() throws IOException {
				return new FileInputStream(new File("/home/allan/apagar.png"));
			}

			@Override
			public String getContentType() {
				return "image/png";
			}
		}, "Imagem Embeded", "logoHinario");

		System.out.println("cid: " + cid);

		email.setHtmlMsg(getHtmlMensagem(notificacaoCanticoEmail, cid));

		System.out.println("Enviando...");
		email.send();
		System.out.println("\n\n\nEnviado.");

		notificacaoCanticoEmail.setDataEnvio(new Date());
		this.canticoDao.salvar(notificacaoCanticoEmail);
	}

	private String getAssunto(final NotificacaoCanticoEmail notificacaoCanticoEmail) {
		if (notificacaoCanticoEmail.getMotivo().equals(Motivo.INSERCAO)) {
			return this.appMessage.getString("label.emailNovoCanticoAssunto", notificacaoCanticoEmail.getCantico().getConsolador().getIrmao().getNome());
		} else if (notificacaoCanticoEmail.getMotivo().equals(Motivo.ALTERACAO)) {
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
		String dataRecebimento = this.sdf.format(notificacaoCanticoEmail.getCantico().getDataRecebimento());
		String dataInsercaoOuAlteracao = this.sdf.format(notificacaoCanticoEmail.getDataAlteracao());

		String htmlMessagem = this.appMessage.getString(chaveHtmlMensagem, id, consolador, recebedor, dataRecebimento, dataInsercaoOuAlteracao);
		htmlMessagem.replace("logoHinario", cid);

		return htmlMessagem;
	}
>>>>>>> c1230cefc90daa56006974161091efc033993abc
}
