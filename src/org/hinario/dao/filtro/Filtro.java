package org.hinario.dao.filtro;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hinario.dao.filtro.anotacoes.CampoNaoFiltravel;
import org.hinario.dao.filtro.anotacoes.CampoPrincipal;
import org.hinario.dao.filtro.anotacoes.FiltroCamposPrincipais;
import org.hinario.dao.filtro.anotacoes.NaoFiltravelPara;
import org.hinario.model.EntidadeBase;

public class Filtro {

	private List<Condicao> condicoes;
	private List<Campo> campos;
	private final Class<? extends Object> clazz;

	public Filtro(final Class<? extends Object> clazz) {
		this.clazz = clazz;
		this.campos = new ArrayList<>();
		this.condicoes = new ArrayList<>();
		this.processaCampos();
	}

	private void processaCampos() {
		this.processaCampos("", clazz, false);
	}

	private void processaCampos(String campoPai, Class<? extends Object> clazz, boolean somenteCamposPrincipais) {
		for (Field fieldTemp : clazz.getDeclaredFields()) {
			if (!fieldTemp.getName().equals("serialVersionUID") && !fieldTemp.getType().equals(byte[].class) && !fieldTemp.isAnnotationPresent(CampoNaoFiltravel.class) && !fieldTemp.isAnnotationPresent(Transient.class) && (!somenteCamposPrincipais || (somenteCamposPrincipais && fieldTemp.isAnnotationPresent(CampoPrincipal.class)))) {
				if (fieldTemp.getType().isAnnotationPresent(Entity.class)) {
					if (fieldTemp.isAnnotationPresent(FiltroCamposPrincipais.class) || (somenteCamposPrincipais && fieldTemp.isAnnotationPresent(CampoPrincipal.class))) {
						processaCampos(((campoPai != null && !campoPai.isEmpty()) ? campoPai + "." : "") + fieldTemp.getName(), fieldTemp.getType(), true);
					} else if (fieldTemp.isAnnotationPresent(NaoFiltravelPara.class) && isNaoFiltravelParaMim(fieldTemp.getAnnotation(NaoFiltravelPara.class))) {
						continue;
					} else {
						processaCampos(fieldTemp.getName(), fieldTemp.getType(), false);
					}
				} else if (isColecaoDeEntidade(fieldTemp)) {
				} else {
					this.campos.add(new Campo(((campoPai != null && !campoPai.isEmpty()) ? campoPai + "." : "") + fieldTemp.getName() + "*" + (clazz.getName() + "." + fieldTemp.getName())));
				}
			}
		}
	}

	private boolean isColecaoDeEntidade(Field campo) {
		for (AnnotatedType typeTemp : campo.getType().getAnnotatedInterfaces()) {
			if (typeTemp.getType().toString().contains("java.util.Collection")) {
				try {
					Class<? extends Object> entityTemp = Class.forName(campo.getGenericType().toString().substring(campo.getGenericType().toString().indexOf("<") + 1, campo.getGenericType().toString().indexOf(">")));
					if (entityTemp.isAnnotationPresent(Entity.class)) {
						return true;
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	private boolean isNaoFiltravelParaMim(NaoFiltravelPara anotacao) {
		for (Class<? extends EntidadeBase> clazzTemp : anotacao.value()) {
			if (this.clazz.equals(clazzTemp)) {
				return true;
			}
		}
		return false;
	}

	public List<Campo> getCampos() {
		return this.campos;
	}

	public List<Condicao> getCondicoes() {
		return condicoes;
	}

}
