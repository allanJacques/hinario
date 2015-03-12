package org.hinario.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hinario.dao.filtro.Filtro;
import org.hinario.model.EntidadeBase;
import org.hinario.model.Ocasiao;
import org.hinario.model.Usuario;
import org.primefaces.model.SortMeta;

public class OcasiaoDAO extends DAOBase implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Long count(Filtro filtro) {
		return (Long) getQueryOrdenadaEFiltrada("select COUNT(o) from Ocasiao o", "o", em, filtro, null, Long.class).getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<? extends EntidadeBase> getLista(Integer inicio, Integer limite, List<SortMeta> multiSortMeta, Filtro filtro) {
		TypedQuery<Usuario> q = (TypedQuery<Usuario>) getQueryOrdenadaEFiltrada("select ocasiao from Ocasiao ocasiao", "ocasiao", em, filtro, multiSortMeta, Ocasiao.class);
		if (inicio != null)
			q.setFirstResult(inicio);
		if (limite != null)
			q.setMaxResults(limite);
		List<Usuario> returN = q.getResultList();
		return returN;
	}

	@Override
	public EntidadeBase getEntidadePorId(Long id) {
		Ocasiao ocasiao = this.em.getReference(Ocasiao.class, id);
		return ocasiao;
	}

}
