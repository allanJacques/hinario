package org.hinario.dao;

import javax.persistence.EntityManager;

public class BasicDAO {

	// protected EntityManagerFactory emf;
	protected EntityManager em;

	public BasicDAO() {
		// this.emf = JPAUtil.getEFM();
		this.em = JPAUtil.getEntityManager();
	}

	public void salvar(final Object object) {
		em.getTransaction().begin();
		em.persist(object);
		em.getTransaction().commit();
	}

}
