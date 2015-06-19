package org.hinario.app;

import java.util.List;

import org.hinario.dao.ConfiguracaoDAO;
import org.hinario.model.Configuracao;

public class AppConfig {

	private static AppConfig instancia;
	private ConfiguracaoDAO dao;
	private List<Configuracao> configuracoes;

	static {
		instancia = new AppConfig();
	}

	private AppConfig() {
		this.dao = new ConfiguracaoDAO();
		this.configuracoes = dao.getConfiguracoes();
	}

	public static AppConfig getInstancia() {
		return instancia;
	}

	public void setConfiguracao(final String nome, final String valor) {
		boolean temNaLista = false;
		for (Configuracao config : this.configuracoes) {
			if (config.getNome().equals(nome)) {
				temNaLista = true;
				config.setValor(valor);
				dao.setConfiguracao(nome, valor);
				break;
			}
		}
		if (!temNaLista) {
			dao.setConfiguracao(nome, valor);
			this.configuracoes = dao.getConfiguracoes();
		}

	}

	public String getValorConfiguracao(final String nome) {
		for (Configuracao config : this.configuracoes) {
			if (config.getNome().equals(nome)) {
				return config.getValor();
			}
		}
		dao.setConfiguracao(nome, "");
		return null;
	}

}
