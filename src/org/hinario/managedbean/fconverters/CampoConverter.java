package org.hinario.managedbean.fconverters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.hinario.util.filtro.Campo;

@FacesConverter(value = "campoConverter")
public class CampoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String valor) {
		return new Campo(valor);
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object valor) {
		return ((Campo) valor).getChave();
	}
}
