package org.hinario.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hinario.dao.filtro.Filtro;
import org.hinario.model.Cantico;
import org.hinario.model.EntidadeBase;
import org.hinario.model.ModoDeCantar;
import org.hinario.model.NotificacaoCanticoEmail;
import org.primefaces.model.SortMeta;

public class CanticoDAO extends DAOBase implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public void remover(final Object cantico) {
		try {
			if (cantico instanceof Cantico) {
				this.em.getTransaction().begin();
				Query q = this.em.createQuery("delete from NotificacaoCanticoEmail where cantico = :cantico");
				q.setParameter("cantico", cantico);
				q.executeUpdate();
				em.getTransaction().commit();
			}
			super.remover(cantico);
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public Long count(Filtro filtro) {
		return (Long) getQueryOrdenadaEFiltrada("select COUNT(cantico) from Cantico cantico left join cantico.ocasioes ocasioes", "cantico", em, filtro, null, Long.class).getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<? extends EntidadeBase> getLista(Integer inicio, Integer limite, List<SortMeta> multiSortMeta, Filtro filtro) {
		TypedQuery<Cantico> q = (TypedQuery<Cantico>) getQueryOrdenadaEFiltrada("select cantico from Cantico cantico left join cantico.ocasioes ocasioes", "cantico", em, filtro, multiSortMeta, Cantico.class);
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

	public void removeNotificacoesEnviadas() {
		this.em.getTransaction().begin();
		this.em.createQuery("delete from NotificacaoCanticoEmail n where n.dataEnvio is not null").executeUpdate();
		this.em.getTransaction().commit();
	}

	public List<NotificacaoCanticoEmail> getNotificacoesPendentes() {
		TypedQuery<NotificacaoCanticoEmail> q = this.em.createQuery("select n from NotificacaoCanticoEmail n where n.dataEnvio is null", NotificacaoCanticoEmail.class);
		return q.getResultList();
	}

	public List<ModoDeCantar> listSugestoesModoDeCantar(final String query) {
		TypedQuery<ModoDeCantar> q = em.createQuery("select modoDeCantar from ModoDeCantar modoDeCantar where upper(modoDeCantar.descricao) like upper(:query)", ModoDeCantar.class);
		q.setParameter("query", "%" + query + "%");
		return q.getResultList();
	}

	public ModoDeCantar getModoDeCantarPorNome(final String modoDeCantar) {
		try {
			TypedQuery<ModoDeCantar> q = em.createQuery("select modoDeCantar from ModoDeCantar modoDeCantar where upper(modoDeCantar.descricao) = upper(:modoDeCantar)", ModoDeCantar.class);
			q.setParameter("modoDeCantar", modoDeCantar);
			return q.getSingleResult();
		} catch (NoResultException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

}
