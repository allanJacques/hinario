package org.hinario.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hinario.model.Irmao;

public abstract class IrmaoDAO extends DAOBase implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<String> listaNomeIrmaos(final String valor) {
		TypedQuery<String> qNomes = this.em.createQuery("select i.nome from Irmao i where upper(i.nome) like upper(:nome)", String.class);
		qNomes.setParameter("nome", this.entrePorcentagens(valor));
		List<String> returN = qNomes.getResultList();
		return returN;
	}

	public Irmao getIrmaoPorNome(final String nome) {
		TypedQuery<Irmao> qIrmao = this.em.createQuery("select i from Irmao i where i.nome = :nome", Irmao.class);
		qIrmao.setParameter("nome", nome);
		return qIrmao.getSingleResult();
	}

}
