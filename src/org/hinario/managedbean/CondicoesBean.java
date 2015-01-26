package org.hinario.managedbean;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hinario.dao.filtro.Operador;

@ManagedBean
@ApplicationScoped
public class CondicoesBean {

	public Operador[] getOperadores() {
		return Operador.values();
	}

}
