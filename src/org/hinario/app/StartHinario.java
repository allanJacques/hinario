package org.hinario.app;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.hinario.dao.CanticoDAO;
import org.hinario.dao.JPAUtil;
import org.hinario.negocio.notificacao.ServicoNotificacaoCanticoEmail;

@WebListener
public class StartHinario implements ServletContextListener {

	public StartHinario() {
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		System.out.println("Inicializando Hinario...");
		JPAUtil.getEntityManager();
		AppConfig.getInstancia();
		new ServicoNotificacaoCanticoEmail().start();
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		System.out.println("Finalizando Hinario...");
		new CanticoDAO().removeNotificacoesEnviadas();
		JPAUtil.closeEntityManager();
	}

}
