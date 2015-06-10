package org.hinario.negocio.managedbean.notificacao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

import javax.activation.DataSource;
import javax.annotation.ManagedBean;
import javax.faces.bean.ApplicationScoped;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.hinario.model.NotificacaoCanticoEmail;

@ManagedBean()
@ApplicationScoped
public class NotificadorPorEmailBean {

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

	}

	public void notificar(NotificacaoCanticoEmail notificacaoCanticoEmail) {

	}

}
