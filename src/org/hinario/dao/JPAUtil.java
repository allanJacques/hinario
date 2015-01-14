package org.hinario.dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManager uniqueEntityManager;

	// private static EntityManagerFactory emf;
	//
	// static {
	// }

	// private static EntityManagerFactory getEFM() {
	// if (emf == null) {
	// emf = Persistence.createEntityManagerFactory("hinario");
	// }
	// return JPAUtil.emf;
	// }

	public static EntityManager getEntityManager() {
		if (JPAUtil.uniqueEntityManager == null) {
			JPAUtil.uniqueEntityManager = Persistence.createEntityManagerFactory("hinario").createEntityManager();
		}
		return JPAUtil.uniqueEntityManager;
	}

	public static void closeEntityManager() {
		if (JPAUtil.uniqueEntityManager == null && JPAUtil.uniqueEntityManager.isOpen()) {
			JPAUtil.uniqueEntityManager.close();
		}
	}

}
