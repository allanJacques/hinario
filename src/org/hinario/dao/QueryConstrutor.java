package org.hinario.dao;

import java.util.Iterator;
import java.util.List;

import org.hinario.dao.filtro.Condicao;
import org.hinario.dao.filtro.Filtro;
import org.hinario.dao.filtro.Operador;
import org.primefaces.model.SortMeta;

public class QueryConstrutor {

	public String getQueryOrdenada(final String sQuery, final List<SortMeta> multiSortMeta, final String alias) {
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

	public String getQueryFiltrada(final String query, final Filtro filtro, final String alias) {

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
		switch (condicao.getOperador()) {
		case IGUAL:
			return valorVARCHAR(condicao.getValor().toString());
		case COMECACOM:
			return valorVARCHARComecaCom(condicao.getValor().toString());
		case CONTEM:
			return valorVARCHARContem(condicao.getValor().toString());
		case DIFERENTE:
			return valorVARCHAR(condicao.getValor().toString());
		case MAIOR:
			return valorVARCHAR(condicao.getValor().toString());
		case MAIORIGUAL:
			return valorVARCHAR(condicao.getValor().toString());
		case MENOR:
			return valorVARCHAR(condicao.getValor().toString());
		case MENORIGUAL:
			return valorVARCHAR(condicao.getValor().toString());
		case NAOCONTEM:
			return valorVARCHARContem(condicao.getValor().toString());
		case TERMINACOM:
			return valorVARCHARTerminaCom(condicao.getValor().toString());
		default:
			return "";
		}

	}

	private String valorVARCHAR(final String valor) {
		return "'" + valor + "'";
	}

	private String valorVARCHARComecaCom(final String valor) {
		return "'" + valor + "%'";
	}

	private String valorVARCHARTerminaCom(final String valor) {
		return "'%" + valor + "'";
	}

	private String valorVARCHARContem(final String valor) {
		return "'%" + valor + "%'";
	}
}
