package org.hinario.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

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

		// TypedQuery<Usuario> qu = (TypedQuery<Usuario>)
		// em.createQuery("select usuario from Usuario usuario join fetch usuario.irmao where  (usuario.email like '%gmail%')  order by usuario.irmao.nome asc",
		// Usuario.class);
		// List<Usuario> usuarios = qu.getResultList();
		// for (Usuario uTemp : usuarios) {
		// System.out.println(uTemp.getEmail());
		// }
		// System.out.println("resultado: " + usuarios.size());

		Query q = em.createQuery("select COUNT(u) from Usuario u where  (u.id like :id)");
		q.setParameter("id", new Long(1L));
		System.out.println(q.getResultList());

		em.getTransaction().commit();
		em.close();
		emf.close();

	}

}
