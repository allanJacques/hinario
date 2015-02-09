package org.hinario.util;

import java.sql.SQLException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Teste {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hinario");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		Query q = em.createQuery("select COUNT(u) from Usuario u where  (u.irmao.dataCadastro > :irmaodataCadastro)", Long.class);
		q.setParameter("irmaodataCadastro", new Date());
		System.out.println(q.getResultList());

		em.getTransaction().commit();
		em.close();
		emf.close();

	}

}
