package org.hinario.negocio.notificacao;

import org.hinario.app.AppConfig;
import org.hinario.dao.CanticoDAO;
import org.hinario.model.Cantico;
import org.hinario.model.NotificacaoCanticoEmail;
import org.hinario.model.enums.Motivo;

public class NotificacaoPorEmailTask extends Thread {

	private Cantico cantico;
	private Motivo motivo;

	public NotificacaoPorEmailTask(final Cantico cantico, final Motivo motivo) {
		this.cantico = cantico;
		this.motivo = motivo;
	}

	@Override
	public void run() {
		boolean aoInserir = Boolean.parseBoolean(AppConfig.getInstancia().getValorConfiguracao("email.aoInserir"));
		boolean aoEditar = Boolean.parseBoolean(AppConfig.getInstancia().getValorConfiguracao("email.aoEditar"));
		if ((aoInserir && this.motivo == Motivo.INSERCAO) || (aoEditar && this.motivo == Motivo.EDICAO)) {
			try {
				CanticoDAO dao = new CanticoDAO();
				NotificacaoCanticoEmail notificacaoCanticoEmail = new NotificacaoCanticoEmail();
				notificacaoCanticoEmail.setMotivo(motivo);
				notificacaoCanticoEmail.setCantico(cantico);
				dao.salvar(notificacaoCanticoEmail);
				NotificadorPorEmail.getInstancia().notificar(notificacaoCanticoEmail);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
