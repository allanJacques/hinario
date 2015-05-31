package org.hinario.negocio.fvalidators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.hinario.app.AppMessage;
import org.hinario.dao.UsuarioDAO;

@FacesValidator("emailUnicoValidator")
public class EmailUnicoValidator implements Validator {

	@Override
	public void validate(FacesContext facesContext, UIComponent component, Object value) throws ValidatorException {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		if (usuarioDAO.emailJaExiste(value.toString())) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, null, new AppMessage().getString("message.emailJaExiste", value)));
		}
	}

}
