package org.hinario.managedbean;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hinario.util.filtro.OperadorCondicional;

@ManagedBean
@ApplicationScoped
public class CondicoesBean {

	public OperadorCondicional[] getOperadores() {
		return OperadorCondicional.values();
	}

}
