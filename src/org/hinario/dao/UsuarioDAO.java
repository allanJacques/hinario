package org.hinario.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hinario.model.Usuario;

public class UsuarioDAO extends BasicDAO {

	public UsuarioDAO() {
	}

	public boolean emailJaExiste(final String email) {
		Query q = this.em.createQuery("select COUNT(u) from Usuario u where email = :email");
		q.setParameter("email", email);
		Long count = (Long) q.getSingleResult();
		return (count > 0);
	}

	public boolean emailJaExisteEmOutroUsuario(final String email, final Long id) {
		Query q = this.em.createQuery("select COUNT(u) from Usuario u where email = :email AND id != :id");
		q.setParameter("email", email);
		q.setParameter("id", id);
		Long count = (Long) q.getSingleResult();
		return (count > 0);
	}

	public Usuario getUsuarioPorId(Long id) {
		Usuario usuario = this.em.getReference(Usuario.class, id);
		return usuario;
	}

	@PostConstruct
	public List<Usuario> getListaUsuario() {
		// TODO fazendo muitos selects no banco, queries desnecessarias
		System.out.println("----------------------------------getListaUsuario()----------------------------------");
		TypedQuery<Usuario> q = this.em.createQuery("select u from Usuario u join u.irmao order by u.id desc", Usuario.class);
		List<Usuario> returN = q.getResultList();
		return returN;
	}

	public List<Usuario> getListaUsuario(int inicio, int limite) {
		// TODO fazendo muitos selects no banco, queries desnecessarias
		System.out.println("----------------------------------getListaUsuario()----------------------------------");
		TypedQuery<Usuario> q = this.em.createQuery("select u from Usuario u join u.irmao order by u.id desc", Usuario.class);
		q.setFirstResult(inicio);
		q.setMaxResults(limite);
		List<Usuario> returN = q.getResultList();
		return returN;
	}

	public Long count() {
		return this.em.createQuery("select COUNT(u) from Usuario u", Long.class).getSingleResult();
	}

}
