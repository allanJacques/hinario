package org.hinario.util;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.hinario.model.Cantico;
import org.hinario.model.Ocasiao;

public class Teste {
	public static void main(String[] args) throws SQLException, ClassNotFoundException, UnsupportedEncodingException {
		try {

			List<Ocasiao> teste = new ArrayList<Ocasiao>();
			System.out.println(teste instanceof Collection);
			System.exit(0);

			EntityManagerFactory emf = Persistence.createEntityManagerFactory("hinario");
			EntityManager em = emf.createEntityManager();

			TypedQuery<Cantico> q = em.createQuery("select cantico from Cantico cantico left join cantico.ocasioes ocasiao where ocasiao.descricao like '%a%'", Cantico.class);
			List<Cantico> canticos = q.getResultList();

			for (Cantico cantico : canticos) {
				System.out.println(cantico);
			}

			em.close();
			emf.close();
		} catch (Exception e) {

		}
	}
}
