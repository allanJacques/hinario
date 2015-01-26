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

		// TypedQuery<Long> qu = (TypedQuery<Long>)
		// em.createQuery("select id from Usuario  where id like '1' ",
		// Long.class);
		// List<Long> idDeUsuarios = qu.getResultList();
		// for (Long uTemp : idDeUsuarios) {
		// System.out.println(uTemp);
		// }
		// System.out.println("resultado: " + idDeUsuarios.size());

		TypedQuery<Usuario> qu = (TypedQuery<Usuario>) em.createQuery("select usuario from Usuario usuario join fetch usuario.irmao where  (usuario.email like '%gmail%')  order by usuario.irmao.nome asc", Usuario.class);
		List<Usuario> usuarios = qu.getResultList();
		for (Usuario uTemp : usuarios) {
			System.out.println(uTemp.getEmail());
		}
		System.out.println("resultado: " + usuarios.size());

		em.getTransaction().commit();
		em.close();
		emf.close();

	}

}
