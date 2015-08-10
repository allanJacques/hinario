package org.hinario.app;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class AppMessage {

	private ResourceBundle resourceBundle;
	private static ResourceBundle resourceBundleStatic;

	public AppMessage() {
		this.resourceBundle = ResourceBundle.getBundle("org.hinario.ui.message.string");
	}

	static {
		AppMessage.resourceBundleStatic = ResourceBundle.getBundle("org.hinario.ui.message.string");
	}

	public String getString(final String chave) {
		try {
			String returN = this.resourceBundle.getString(chave);
			if (returN.equals(null)) {
				return "*" + chave + "*";
			}
			return returN;
		} catch (MissingResourceException ex) {
			return "*" + chave + "*";
		}
	}

	public String getString(final String chave, Object... valores) {
		String returN = this.resourceBundle.getString(chave);
		if (returN.equals(null)) {
			return "*" + chave + "*";
		}
		return MessageFormat.format(returN, valores);
	}

	public static String getStaticString(final String chave) {
		try {
			String returN = resourceBundleStatic.getString(chave);
			if (returN.equals(null)) {
				return "*" + chave + "*";
			}
			return returN;
		} catch (MissingResourceException ex) {
			return "*" + chave + "*";
		}
	}

	public static String getStaticString(final String chave, Object... valores) {
		String returN = resourceBundleStatic.getString(chave);
		if (returN.equals(null)) {
			return "*" + chave + "*";
		}
		return MessageFormat.format(returN, valores);
	}
}
