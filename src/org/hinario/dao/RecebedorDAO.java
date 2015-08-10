package org.hinario.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hinario.dao.filtro.Filtro;
import org.hinario.model.EntidadeBase;
import org.hinario.model.Recebedor;
import org.primefaces.model.SortMeta;

public class RecebedorDAO extends IrmaoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Long count(Filtro filtro) {
		return (Long) getQueryOrdenadaEFiltrada("select COUNT(r) from Recebedor r", "r", em, filtro, null, Long.class).getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<? extends EntidadeBase> getLista(Integer inicio, Integer limite, List<SortMeta> multiSortMeta, Filtro filtro) {
		TypedQuery<Recebedor> q = (TypedQuery<Recebedor>) getQueryOrdenadaEFiltrada("select r from Recebedor r", "r", em, filtro, multiSortMeta, Recebedor.class);
		if (inicio != null)
			q.setFirstResult(inicio);
		if (limite != null)
			q.setMaxResults(limite);
		List<Recebedor> returN = q.getResultList();
		return returN;
	}

	@Override
	public EntidadeBase getEntidadePorId(Long id) {
		Recebedor recebedor = this.em.getReference(Recebedor.class, id);
		return recebedor;
	}

	public List<Recebedor> listaRecebedores(String valor) {
		TypedQuery<Recebedor> q = this.em.createQuery("select r from Recebedor r join fetch r.irmao where upper(r.irmao.nome) like upper(:nome)", Recebedor.class);
		q.setParameter("nome", this.entrePorcentagens(valor));
		return q.getResultList();
	}
	
	public List<String> listaNomeIrmaosParaRecebedor(String valor) {
		TypedQuery<String> qNomes = this.em.createQuery("select i.nome from Irmao i where upper(i.nome) like upper(:nome) and i.nome not in (select irmao.nome from Recebedor)", String.class);
		qNomes.setParameter("nome", this.entrePorcentagens(valor));
		List<String> returN = qNomes.getResultList();
		return returN;
	}

}
