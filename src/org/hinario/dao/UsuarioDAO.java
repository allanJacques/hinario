package org.hinario.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hinario.dao.filtro.Filtro;
import org.hinario.model.Usuario;
import org.primefaces.model.SortMeta;

public class UsuarioDAO extends IrmaoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public UsuarioDAO() {
		super();
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

	@Override
	public Usuario getEntidadePorId(final Long id) {
		Usuario usuario = this.em.getReference(Usuario.class, id);
		return usuario;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> getLista(final Integer inicio, final Integer limite, final List<SortMeta> multiSortMeta, final Filtro filtro) {
		TypedQuery<Usuario> q = (TypedQuery<Usuario>) getQueryOrdenadaEFiltrada("select usuario from Usuario usuario join fetch usuario.irmao", "usuario", em, filtro, multiSortMeta, Usuario.class);
		if (inicio != null)
			q.setFirstResult(inicio);
		if (limite != null)
			q.setMaxResults(limite);
		List<Usuario> returN = q.getResultList();
		return returN;
	}

	@Override
	public Long count(final Filtro filtro) {
		return (Long) getQueryOrdenadaEFiltrada("select COUNT(u) from Usuario u", "u", em, filtro, null, Long.class).getSingleResult();
	}

	public Usuario valida(String email, String senha) {
		Usuario returN = null;
		try {
			TypedQuery<Usuario> q = this.em.createQuery("select u from Usuario u where email = :email and senha = :senha", Usuario.class);
			q.setParameter("email", email);
			q.setParameter("senha", senha);
			returN = q.getSingleResult();
		} catch (NoResultException nre) {
			System.out.println(nre.getClass() + ": " + "Senha incorreta");
		}
		return returN;
	}
}
