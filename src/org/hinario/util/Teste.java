package org.hinario.util;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Teste {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		EntityManagerFactory myEntityManagerFactory;

		Properties p = new Properties();

//		p.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
//		p.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/hinario");
//		p.put("javax.persistence.jdbc.user", "root");
		p.put("javax.persistence.jdbc.password", "root");
//		p.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
//		p.put("hibernate.hbm2ddl.auto", "update");
//		p.put("hibernate.show_sql", "true");
//		p.put("hibernate.format_sql", "true");
//		p.put("hibernate.c3p0.min_size", "5");
//		p.put("hibernate.c3p0.max_size", "20");
//		p.put("hibernate.c3p0.timeout", "6000");
//		p.put("hibernate.c3p0.max_statements", "100");

		myEntityManagerFactory = Persistence.createEntityManagerFactory("manual", p);

		System.out.println("Acho que conectou!!");
		// System.exit(0);

		// EntityManagerFactory emf =
		// Persistence.createEntityManagerFactory("hinario");
		EntityManagerFactory emf = myEntityManagerFactory;
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		Query q = em.createQuery("select COUNT(u) from Usuario u", Long.class);
		System.out.println(q.getResultList());

		em.getTransaction().commit();
		em.close();
		emf.close();

	}

}
