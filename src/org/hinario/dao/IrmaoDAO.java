package org.hinario.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;

public abstract class IrmaoDAO extends DAOBase implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<String> listaNomeIrmaos(final String valor) {
		TypedQuery<String> qNomes = this.em.createQuery("select i.nome from Irmao i where upper(i.nome) like upper(:nome)", String.class);
		qNomes.setParameter("nome", "%" + valor + "%");
		List<String> returN = qNomes.getResultList();
		return returN;
	}

}
