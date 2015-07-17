package org.hinario.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hinario.model.Configuracao;

public class ConfiguracaoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager em;

	public ConfiguracaoDAO() {
		this.em = JPAUtil.getEntityManager();
	}

	public List<Configuracao> getConfiguracoes() {
		return this.em.createQuery("select configuracao from Configuracao configuracao", Configuracao.class).getResultList();
	}

	public void setConfiguracao(final String nome, final String valor) {
		em.getTransaction().begin();
		Query qUpdate = em.createQuery("update Configuracao c set c.valor = :valor where c.nome = :nome");
		qUpdate.setParameter("nome", nome);
		qUpdate.setParameter("valor", valor);
		int linhasAlteradas = qUpdate.executeUpdate();
		if (!(linhasAlteradas > 0)) {
			Configuracao configTemp = new Configuracao(nome, valor);
			em.persist(configTemp);
		}
		em.getTransaction().commit();
	}

	public Configuracao getConfiguration(final String nome) {
		return this.em.getReference(Configuracao.class, nome);
	}

}
