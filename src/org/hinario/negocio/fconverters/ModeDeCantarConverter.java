package org.hinario.negocio.fconverters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.hinario.dao.CanticoDAO;
import org.hinario.model.ModoDeCantar;

@FacesConverter(forClass = ModoDeCantar.class)
public class ModeDeCantarConverter implements Converter {

	private final CanticoDAO dao = new CanticoDAO();

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ModoDeCantar modoDeCantar = dao.getModoDeCantarPorNome(value);
		if (modoDeCantar != null) {
			return modoDeCantar;
		}
		ModoDeCantar modoDeCantarTemp = new ModoDeCantar();
		modoDeCantarTemp.setDescricao(value);
		return modoDeCantarTemp;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			return ((ModoDeCantar) value).getDescricao();
		}
		
		return null;
	}

}
