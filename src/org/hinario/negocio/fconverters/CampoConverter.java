package org.hinario.negocio.fconverters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.hinario.dao.filtro.Campo;

@FacesConverter(forClass = Campo.class)
public class CampoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String valor) {
		return new Campo(valor);
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object valor) {
		if (valor != null)
			return ((Campo) valor).getChave();
		else
			return null;
	}
}
