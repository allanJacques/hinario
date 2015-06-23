package org.hinario.util;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.hinario.model.Arquivo;
import org.hinario.model.Cantico;
import org.hinario.model.NotificacaoCanticoEmail;
import org.hinario.model.Ocasiao;

public class Teste {
	public static void main(String[] args) throws SQLException, ClassNotFoundException, UnsupportedEncodingException {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hinario");
		EntityManager em = emf.createEntityManager();

		TypedQuery<NotificacaoCanticoEmail> q = em.createQuery("select from NotificacaoCanticoEmail  where dataEnvio is null", NotificacaoCanticoEmail.class);
		q.getResultList();

		em.getTransaction().begin();

		TypedQuery<Cantico> qCanticos = em.createQuery("select cantico from Cantico cantico", Cantico.class);

		System.out.println("================================antes do getResultList()");
		List<Cantico> returN = qCanticos.getResultList();
		System.out.println("===============================depois do getResultList()");
		for (Cantico cTemp : returN) {
			System.out.println("------------------------------");
			System.out.println(cTemp.getConsolador().getIrmao().getNome());
			System.out.println(cTemp.getRecebedor().getIrmao().getNome());
			System.out.println(cTemp.getDataCadastro());
			System.out.println(cTemp.getDataRecebimento());
			System.out.println(cTemp.getObservacao());
			for (Ocasiao oTemp : cTemp.getOcasioes()) {
				System.out.println("\t" + oTemp.getDescricao());
			}
			System.out.println("---");
			for (Arquivo aTemp : cTemp.getArquivos()) {
				System.out.println("\t" + aTemp.getNome());
			}
		}

		em.getTransaction().commit();
		em.close();
		emf.close();

	}
}
