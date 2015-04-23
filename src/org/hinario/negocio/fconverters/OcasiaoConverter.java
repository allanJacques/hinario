package org.hinario.negocio.fconverters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.hinario.dao.OcasiaoDAO;
import org.hinario.model.Ocasiao;

@FacesConverter(forClass = Ocasiao.class, value="OcasiaoConverter")
public class OcasiaoConverter implements Converter {

	private OcasiaoDAO dao = new OcasiaoDAO();

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return dao.getEntidadePorId(Long.parseLong(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return ((Ocasiao) value).getId().toString();
	}

}
