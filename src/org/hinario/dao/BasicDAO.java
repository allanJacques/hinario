package org.hinario.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hinario.dao.filtro.Filtro;
import org.primefaces.model.SortMeta;

public class BasicDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	protected EntityManager em;
	protected QueryConstrutor queryConstrutor = new QueryConstrutor();

	public BasicDAO() {
		this.em = JPAUtil.getEntityManager();
	}

	public void salvar(Object object) {
		em.getTransaction().begin();
		em.persist(object);
		object = em.merge(object);
		em.getTransaction().commit();
	}

	public void remover(Object object) {
		em.getTransaction().begin();
		em.remove(em.merge(object));
		em.getTransaction().commit();
	}

	public Query getQueryOrdenadaEFiltrada(final String stringQuery, final String alias, final EntityManager entityManager, final Filtro filtro, final List<SortMeta> multiSortMeta, final Class<? extends Object> clazz) {
		return this.queryConstrutor.getQueryOrdenadaEFiltrada(stringQuery, alias, entityManager, filtro, multiSortMeta, clazz);
	}
}
