package org.hinario.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hinario.dao.filtro.Filtro;
import org.hinario.model.Cantico;
import org.hinario.model.EntidadeBase;
import org.primefaces.model.SortMeta;

public class CanticoDAO extends DAOBase implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Long count(Filtro filtro) {
		return (Long) getQueryOrdenadaEFiltrada("select COUNT(c) from Cantico c", "c", em, filtro, null, Long.class).getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<? extends EntidadeBase> getLista(Integer inicio, Integer limite, List<SortMeta> multiSortMeta, Filtro filtro) {
		TypedQuery<Cantico> q = (TypedQuery<Cantico>) getQueryOrdenadaEFiltrada("select cantico from Cantico cantico", "cantico", em, filtro, multiSortMeta, Cantico.class);
		if (inicio != null)
			q.setFirstResult(inicio);
		if (limite != null)
			q.setMaxResults(limite);
		List<Cantico> returN = q.getResultList();
		return returN;
	}

	@Override
	public EntidadeBase getEntidadePorId(Long id) {
		Cantico cantico = this.em.getReference(Cantico.class, id);
		return cantico;
	}

}
