package org.hinario.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hinario.dao.filtro.Filtro;
import org.hinario.model.Consolador;
import org.hinario.model.EntidadeBase;
import org.primefaces.model.SortMeta;

public class ConsoladorDAO extends IrmaoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Long count(Filtro filtro) {
		return (Long) getQueryOrdenadaEFiltrada("select COUNT(c) from Consolador c", "c", em, filtro, null, Long.class).getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<? extends EntidadeBase> getLista(Integer inicio, Integer limite, List<SortMeta> multiSortMeta, Filtro filtro) {
		TypedQuery<Consolador> q = (TypedQuery<Consolador>) getQueryOrdenadaEFiltrada("select c from Consolador c", "c", em, filtro, multiSortMeta, Consolador.class);
		if (inicio != null)
			q.setFirstResult(inicio);
		if (limite != null)
			q.setMaxResults(limite);
		List<Consolador> returN = q.getResultList();
		return returN;
	}

	@Override
	public EntidadeBase getEntidadePorId(Long id) {
		Consolador consolador = this.em.getReference(Consolador.class, id);
		return consolador;
	}

	public List<Consolador> listaConsoladores(String valor) {
		TypedQuery<Consolador> q = this.em.createQuery("select c from Consolador c join fetch c.irmao where upper(c.irmao.nome) like upper(:nome)", Consolador.class);
		q.setParameter("nome", this.entrePorcentagens(valor));
		return q.getResultList();
	}

	public List<String> listaNomeIrmaosParaConsolador(String valor) {
		TypedQuery<String> qNomes = this.em.createQuery("select i.nome from Irmao i where upper(i.nome) like upper(:nome) and i.nome not in (select irmao.nome from Consolador)", String.class);
		qNomes.setParameter("nome", this.entrePorcentagens(valor));
		List<String> returN = qNomes.getResultList();
		return returN;
	}

}
