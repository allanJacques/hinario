package org.hinario.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hinario.dao.filtro.anotacoes.CampoNaoFiltravel;
import org.hinario.dao.filtro.anotacoes.NomeElementoEmColecao;

@Entity
@NomeElementoEmColecao("ocasiao")
public class Ocasiao extends EntidadeBase implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@CampoNaoFiltravel
	private Long id;

	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int numeroPrimo = 31;
		int resultado = 1;
		resultado = numeroPrimo * resultado + ((descricao == null) ? 0 : descricao.hashCode());
		resultado = numeroPrimo * resultado + ((id == null) ? 0 : id.hashCode());
		return resultado;
	}

	@Override
	public boolean equals(Object valor) {
		if (this == valor)
			return true;
		if (valor == null)
			return false;
		if (getClass() != valor.getClass())
			return false;
		Ocasiao outro = (Ocasiao) valor;
		if (descricao == null) {
			if (outro.descricao != null)
				return false;
		} else if (!descricao.equals(outro.descricao))
			return false;
		if (id == null) {
			if (outro.id != null)
				return false;
		} else if (!id.equals(outro.id))
			return false;
		return true;
	}

}
