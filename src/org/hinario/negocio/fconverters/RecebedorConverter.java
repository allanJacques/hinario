package org.hinario.negocio.fconverters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.hinario.dao.RecebedorDAO;
import org.hinario.model.Recebedor;

@FacesConverter(forClass = Recebedor.class)
public class RecebedorConverter implements Converter {

	private final RecebedorDAO dao = new RecebedorDAO();

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return dao.getEntidadePorId(Long.parseLong(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return ((Recebedor) value).getId().toString();
	}

}
