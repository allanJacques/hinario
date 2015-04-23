package org.hinario.managedbean.fvalidators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

import org.hinario.app.AppMessage;

@FacesValidator(value = "imageValidator")
public class ImageValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		Part part = (Part) value;
		if (value != null)
			if (!part.getContentType().contains("image/")) {
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, null, new AppMessage().getString("message.arquivoNaoEImagem", part.getSubmittedFileName()));
				throw new ValidatorException(fm);
			}
	}

}
