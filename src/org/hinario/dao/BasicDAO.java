package org.hinario.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;

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

}
