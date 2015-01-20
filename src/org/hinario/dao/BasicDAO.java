package org.hinario.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.primefaces.model.SortMeta;

public class BasicDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	// protected EntityManagerFactory emf;
	protected EntityManager em;

	public BasicDAO() {
		// this.emf = JPAUtil.getEFM();
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

	public String getQueryOrdenada(String sQuery, final List<SortMeta> multiSortMeta, final String alias) {
		if (multiSortMeta != null && sQuery != null && !multiSortMeta.isEmpty() && !sQuery.isEmpty()) {
			StringBuilder sbQuery = new StringBuilder(sQuery);
			for (SortMeta sortMetaTemp : multiSortMeta) {
				if (!sbQuery.toString().contains("order by")) {
					sbQuery.append(" order by ");
				} else {
					sbQuery.append(" , ");
				}
				sbQuery.append(alias).append(".").append(sortMetaTemp.getSortField());
				sbQuery.append((sortMetaTemp.getSortOrder().toString().equals("ASCENDING") ? " asc " : " desc "));
			}
			return sbQuery.toString();
		} else {
			return sQuery;
		}
	}

}
