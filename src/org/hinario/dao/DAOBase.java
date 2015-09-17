package org.hinario.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hinario.dao.filtro.Filtro;
import org.hinario.dao.filtro.QueryConstrutor;
import org.hinario.model.EntidadeBase;
import org.primefaces.model.SortMeta;

public abstract class DAOBase implements Serializable {

	private static final long serialVersionUID = 1L;

	protected EntityManager em;
	protected QueryConstrutor queryConstrutor = new QueryConstrutor();

	public DAOBase() {
		this.em = JPAUtil.getEntityManager();
	}

	public void salvar(Object object) {
		try {
			em.getTransaction().begin();
			em.persist(object);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
			throw ex;
		}
	}

	public void remover(Object object) {
		try {
			em.getTransaction().begin();
			Object oTemp = em.merge(object);
			em.remove(oTemp);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
			throw ex;
		}
	}

	public Object atualizar(Object object) {
		this.em.getTransaction().begin();
		this.em.getTransaction().rollback();
		Object returN = this.em.find(object.getClass(), ((EntidadeBase) object).getId());
		object = returN;
		return returN;
	}

	public Query getQueryOrdenadaEFiltrada(final String stringQuery, final String alias, final EntityManager entityManager, final Filtro filtro, final List<SortMeta> multiSortMeta, final Class<? extends Object> clazz) {
		return this.queryConstrutor.getQueryOrdenadaEFiltrada(stringQuery, alias, entityManager, filtro, multiSortMeta, clazz);
	}

	public abstract Long count(final Filtro filtro);

	public abstract List<? extends EntidadeBase> getLista(final Integer inicio, final Integer limite, final List<SortMeta> multiSortMeta, final Filtro filtro);

	public abstract EntidadeBase getEntidadePorId(final Long id);

	protected String entrePorcentagens(final String valor) {
		return "%" + valor + "%";
	}
}
