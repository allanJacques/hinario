package org.hinario.util;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class Teste {
	public static void main(String[] args) throws SQLException, ClassNotFoundException, UnsupportedEncodingException {
		try {

			EntityManagerFactory emf = Persistence.createEntityManagerFactory("hinario");
			EntityManager em = emf.createEntityManager();

			// TypedQuery<Cantico> q =
			// em.createQuery("select cantico from Cantico cantico left join cantico.ocasioes ocasioes where upper(ocasioes.descricao) like '%a%'",
			// Cantico.class);
			// List<Cantico> canticos = q.getResultList();

			TypedQuery<String> q = em.createQuery("select i.nome from Irmao i where upper(i.nome) like upper('%LUC%') and i.nome not in (select irmao.nome from Consolador)", String.class);
			//TypedQuery<String> q = em.createQuery("select irmao.nome from Consolador ", String.class);
			List<String> count = q.getResultList();
			System.out.println(count);

			// for (Cantico cantico : canticos) {
			// System.out.println(cantico);
			// }

			em.close();
			emf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
