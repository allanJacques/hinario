package org.hinario.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hinario.dao.filtro.Filtro;
import org.hinario.model.Usuario;
import org.primefaces.model.SortMeta;

public class UsuarioDAO extends BasicDAO implements Serializable {

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

	public Usuario getUsuarioPorId(Long id) {
		Usuario usuario = this.em.getReference(Usuario.class, id);
		return usuario;
	}

	@PostConstruct
	public List<Usuario> getListaUsuario() {
		return getListaUsuario(null, null, null, null);
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> getListaUsuario(final Integer inicio, final Integer limite, final List<SortMeta> multiSortMeta, final Filtro filtro) {
		TypedQuery<Usuario> q = (TypedQuery<Usuario>) getQueryOrdenadaEFiltrada("select usuario from Usuario usuario join fetch usuario.irmao", "usuario", em, filtro, multiSortMeta, Usuario.class);
		if (inicio != null)
			q.setFirstResult(inicio);
		if (limite != null)
			q.setMaxResults(limite);
		List<Usuario> returN = q.getResultList();
		return returN;
	}

	public Long count() {
		return count(null);
	}

	public Long count(final Filtro filtro) {
		return (Long) getQueryOrdenadaEFiltrada("select COUNT(u) from Usuario u", "u", em, filtro, null, Long.class).getSingleResult();
	}

	public Usuario valida(String email, String senha) {
		TypedQuery<Usuario> q = this.em.createQuery("select u from Usuario u where email = :email and senha = :senha", Usuario.class);
		q.setParameter("email", email);
		q.setParameter("senha", senha);
		return q.getSingleResult();
	}
}
