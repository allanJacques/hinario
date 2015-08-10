package org.hinario.dao;

import java.io.Serializable;

import javax.persistence.TypedQuery;

import org.hinario.model.Irmao;

public abstract class IrmaoDAO extends DAOBase implements Serializable {

	private static final long serialVersionUID = 1L;

	public Irmao getIrmaoPorNome(final String nome) {
		TypedQuery<Irmao> qIrmao = this.em.createQuery("select i from Irmao i where i.nome = :nome", Irmao.class);
		qIrmao.setParameter("nome", nome);
		return qIrmao.getSingleResult();
	}

}
