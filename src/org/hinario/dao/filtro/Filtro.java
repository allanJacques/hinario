package org.hinario.dao.filtro;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

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
		this.processaCampos("", clazz);
	}

	private void processaCampos(String campoPai, Class<? extends Object> clazz) {
		for (Field fieldTemp : clazz.getDeclaredFields()) {
			if (!fieldTemp.getName().equals("serialVersionUID") && !fieldTemp.getType().equals(byte[].class) && !fieldTemp.isAnnotationPresent(CampoNaoFiltravel.class) && !fieldTemp.isAnnotationPresent(Transient.class)) {
				if (fieldTemp.getType().isAnnotationPresent(Entity.class)) {
					processaCampos(fieldTemp.getName(), fieldTemp.getType());
				} else
					this.campos.add(new Campo(((campoPai != null && !campoPai.isEmpty()) ? campoPai + "." : "") + fieldTemp.getName() + "*" + (clazz.getName() + "." + fieldTemp.getName())));
			}
		}
	}

	public List<Campo> getCampos() {
		return this.campos;
	}

	public List<Condicao> getCondicoes() {
		return condicoes;
	}

}
