package org.hinario.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

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
		TypedQuery<Usuario> qu = (TypedQuery<Usuario>) em.createQuery("select u from Usuario u join fetch u.irmao", Usuario.class);
		List<Usuario> usuarios = qu.getResultList();
		for (Usuario uTemp : usuarios) {
			System.out.println("Irmão: " + uTemp.getIrmao().getNome());
		}

		em.getTransaction().commit();
		em.close();
		emf.close();

	}

}
