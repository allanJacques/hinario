package org.hinario.negocio.fconverters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.hinario.dao.ConsoladorDAO;
import org.hinario.model.Consolador;

@FacesConverter(forClass = Consolador.class)
public class ConsoladorConverter implements Converter {

	private final ConsoladorDAO dao = new ConsoladorDAO();

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return dao.getEntidadePorId(Long.parseLong(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return ((Consolador) value).getId().toString();
	}

}
