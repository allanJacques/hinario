package org.hinario.managedbean.fconverters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.hinario.app.AppMessage;
import org.hinario.util.filtro.Campo;

@FacesConverter(value = "campoConverter")
public class CampoConverter implements Converter {

	private AppMessage appMessage = new AppMessage();

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String valor) {
		if (valor.split(":").length == 2)
			return new Campo(valor.split(":")[0], valor.split(":")[1], this.appMessage.getString(valor.split(":")[1]));
		else
			return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object valor) {
		Campo returN = ((Campo) getAsObject(facesContext, uiComponent, valor.toString()));
		if (returN != null)
			return returN.getDescricao();
		else
			return null;
	}
}
