package org.hinario.negocio.notificacao;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.mail.EmailException;
import org.hinario.app.AppConfig;
import org.hinario.dao.CanticoDAO;
import org.hinario.negocio.notificacao.NotificadorPorEmail;
import org.hinario.model.NotificacaoCanticoEmail;

public class ServicoNotificacaoCanticoEmail {

	private CanticoDAO dao = new CanticoDAO();
	private NotificadorPorEmail notificador = NotificadorPorEmail.getInstancia();

	public void start() {
		if (Boolean.parseBoolean(AppConfig.getInstancia().getValorConfiguracao("email.servico"))) {
			this.notificar();
		}

	}

	private void notificar() {
		Timer timer = new Timer();
		long periodo = Long.parseLong(AppConfig.getInstancia().getValorConfiguracao("email.servico.frequenciaEmMinutos")) * 1000 * 60;
		timer.schedule(new ServicoNotificacaoCanticoEmailTask(), 0, periodo);

	}

	private class ServicoNotificacaoCanticoEmailTask extends TimerTask {
		@Override
		public void run() {
			List<NotificacaoCanticoEmail> notificacoesPendentes = ServicoNotificacaoCanticoEmail.this.dao.getNotificacoesPendentes();
			for (NotificacaoCanticoEmail notTemp : notificacoesPendentes) {
				try {
					ServicoNotificacaoCanticoEmail.this.notificador.notificar(notTemp);
				} catch (EmailException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
