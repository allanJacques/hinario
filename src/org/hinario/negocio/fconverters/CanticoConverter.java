package org.hinario.negocio.fconverters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.hinario.dao.CanticoDAO;
import org.hinario.model.Cantico;

@FacesConverter(forClass = Cantico.class)
public class CanticoConverter implements Converter {

	private CanticoDAO dao = new CanticoDAO();

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Long id = Long.parseLong(value);
		return dao.getEntidadePorId(id);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return String.valueOf(((Cantico) value).getId());
	}

}
