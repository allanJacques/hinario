package org.hinario.util;

public class StringUtil {

	public String getsubString(final String valor, final int tamanho) {
		if (valor.length() <= tamanho) {
			return valor;
		} else {
			return valor.substring(tamanho);
		}
	}
}
