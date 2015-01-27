package org.hinario.dao;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hinario.dao.filtro.Condicao;
import org.hinario.dao.filtro.Filtro;
import org.hinario.dao.filtro.Operador;
import org.primefaces.model.SortMeta;

public class QueryConstrutor {

	private String getQueryOrdenada(final String sQuery, final List<SortMeta> multiSortMeta, final String alias) {
		if (multiSortMeta != null && sQuery != null && !multiSortMeta.isEmpty() && !sQuery.isEmpty()) {
			StringBuilder sbQuery = new StringBuilder(sQuery);
			for (SortMeta sortMetaTemp : multiSortMeta) {
				if (!sbQuery.toString().contains("order by")) {
					sbQuery.append(" order by ");
				} else {
					sbQuery.append(" , ");
				}
				sbQuery.append(alias).append(".").append(sortMetaTemp.getSortField());
				sbQuery.append((sortMetaTemp.getSortOrder().toString().equals("ASCENDING") ? " asc " : " desc "));
			}
			return sbQuery.toString();
		} else {
			return sQuery;
		}
	}

	private String getQueryFiltrada(final String query, final Filtro filtro, final String alias) {

		StringBuilder clausulaWhere = new StringBuilder();
		StringBuilder sbQuery = new StringBuilder(query);
		clausulaWhere.append(" where ");
		if (filtro != null && !filtro.getCondicoes().isEmpty()) {
			for (Iterator<Condicao> it = filtro.getCondicoes().iterator(); it.hasNext();) {
				Condicao condTemp = it.next();
				clausulaWhere.append(" (" + this.getCondicao(condTemp, alias) + ") ");
				if (it.hasNext()) {
					clausulaWhere.append(" and ");
				}
			}
		} else {
			clausulaWhere.append(" (1=1) ");
		}

		int index = sbQuery.indexOf("order");
		if (index != -1) {
			sbQuery.insert(index, clausulaWhere);
		} else {
			sbQuery.append(clausulaWhere);
		}
		return sbQuery.toString();
	}

	private String getCondicao(final Condicao condicao, final String alias) {
		return (alias + "." + condicao.getCampo().getNome() + this.getOperador(condicao.getOperador()) + getValor(condicao));
	}

	private String getOperador(final Operador operador) {
		switch (operador) {
		case IGUAL:
			return " = ";
		case COMECACOM:
			return " like ";
		case CONTEM:
			return " like ";
		case CONTEMPALAVRAS:
			return " like ";
		case DIFERENTE:
			return " <> ";
		case MAIOR:
			return " > ";
		case MAIORIGUAL:
			return " >= ";
		case MENOR:
			return " < ";
		case MENORIGUAL:
			return " <= ";
		case NAOCONTEM:
			return " not like ";
		case TERMINACOM:
			return " like ";
		default:
			return "";
		}
	}

	private String getValor(Condicao condicao) {
		if (!condicao.getCampo().getType().equals(Date.class))
			switch (condicao.getOperador()) {
			case IGUAL:
				return valorVARCHAR(condicao);
			case COMECACOM:
				return valorVARCHARComecaCom(condicao);
			case CONTEM:
				return valorVARCHARContem(condicao);
			case DIFERENTE:
				return valorVARCHAR(condicao);
			case MAIOR:
				return valorVARCHAR(condicao);
			case MAIORIGUAL:
				return valorVARCHAR(condicao);
			case MENOR:
				return valorVARCHAR(condicao);
			case MENORIGUAL:
				return valorVARCHAR(condicao);
			case NAOCONTEM:
				return valorVARCHARContem(condicao);
			case TERMINACOM:
				return valorVARCHARTerminaCom(condicao);
			default:
				return "";
			}
		else
			return valorParametrizado(condicao);

	}

	private String valorVARCHAR(final Condicao condicao) {
		return "'" + condicao.getValor() + "'";
	}

	private String valorVARCHARComecaCom(final Condicao condicao) {
		return "'" + condicao.getValor() + "%'";
	}

	private String valorVARCHARTerminaCom(final Condicao condicao) {
		return "'%" + condicao.getValor() + "'";
	}

	private String valorVARCHARContem(final Condicao condicao) {
		return "'%" + condicao.getValor() + "%'";
	}

	private String valorParametrizado(Condicao condicao) {
		return ":" + condicao.getCampo().getNome();
	}

	public Query getQueryOrdenadaEFiltrada(String stringQuery, String alias, EntityManager entityManager, Filtro filtro, List<SortMeta> multiSortMeta, Class<? extends Object> clazz) {
		String sQuery = getQueryFiltrada(getQueryOrdenada(stringQuery, multiSortMeta, alias), filtro, alias);
		if (clazz != null) {
			return entityManager.createQuery(sQuery, clazz);
		} else {
			return entityManager.createQuery(sQuery);
		}
	}

}
