package org.hinario.managedbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class TesteBean {

	private String valor;

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		System.out.println("Valor setado: " + valor);
		this.valor = valor;
	}

	public void submit() {
		System.out.println("Valor submetido: " + this.valor);
	}
}
