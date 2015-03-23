package org.hinario.util;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.hinario.model.Irmao;

public class Teste {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hinario");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		TypedQuery<String> qNomes = em.createQuery("select upper(i.nome) from Irmao i", String.class);
//		TypedQuery<String> qNomes = em.createQuery("select i.nome from Irmao i where upper(i.nome) like upper(:nome)", String.class);
//		qNomes.setParameter("nome", "all");
		List<String> returN = qNomes.getResultList();

		for (String string : returN) {
			System.out.println(string);
		}

		TypedQuery<Irmao> qIrmao = em.createQuery("select i from Irmao i where UPPER(i.nome) like UPPER(:nome) ", Irmao.class);
		//TypedQuery<Irmao> qIrmao = em.createQuery("select i from Irmao i where upper(i.nome) like upper(:nome)", Irmao.class);
		qIrmao.setParameter("nome", "%ISA%");
		List<Irmao> returN2 = qIrmao.getResultList();

		for (Irmao irmao : returN2) {
			System.out.println(irmao.getNome() + irmao.getObservacao());
		}

		em.getTransaction().commit();
		em.close();
		emf.close();

	}
}
