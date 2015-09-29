package org.hinario.negocio.fconverters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.hinario.dao.CanticoDAO;
import org.hinario.model.Arquivo;

@FacesConverter(forClass = Arquivo.class)
public class ArquivoConverter implements Converter {
	private CanticoDAO dao = new CanticoDAO();

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return dao.getArquivo(new Long(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return String.valueOf(((Arquivo) value).getId());
	}

}
