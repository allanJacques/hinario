package org.hinario.util;

import java.lang.reflect.InvocationTargetException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.hinario.dao.filtro.Operador;

public class Teste {

	public static void main(String[] args) {
//		System.out.println("iniciando");
//		try {
//			System.out.println(Class.forName("org.hinario.dao.filtro.Operador").getMethod("values", null).invoke(null, null));
//			Operador[] array = (Operador[]) Class.forName("org.hinario.dao.filtro.Operador").getMethod("values", null).invoke(null, null);
//			for (Object o : array) {
//				System.out.println(o);
//			}
//		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("finalizando");
//
//		System.exit(0);
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
