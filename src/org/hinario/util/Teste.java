package org.hinario.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.Criteria;
import org.hinario.model.Irmao;
import org.hinario.model.Usuario;

public class Teste {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hinario");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		// Query q = em.createQuery("select c from Cantico c");
		//
		// q.getResultList();

		// TypedQuery<Usuario> qu = (TypedQuery)
		// em.createQuery("select i from Irmao i join i.usuario ",
		// Usuario.class);
		TypedQuery<Usuario> qu = (TypedQuery) em.createQuery("select u from Usuario u join u.irmao", Usuario.class);
		List<Usuario> usuarios = qu.getResultList();
		for (Usuario uTemp : usuarios) {
			System.out.println(uTemp.getIrmao().getNome());
		}

		em.getTransaction().commit();
		em.close();
		emf.close();

	}

}
