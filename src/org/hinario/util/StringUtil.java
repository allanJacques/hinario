package org.hinario.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class StringUtil {

	public String getsubString(final String valor, final int tamanho) {
		if (valor.length() <= tamanho) {
			return valor;
		} else {
			return valor.substring(tamanho);
		}
	}

	public String StringToHex(final String valor) {
		return new String(Hex.encodeHex(valor.getBytes(Hex.DEFAULT_CHARSET), false));
	}

	public String HexToString(final String hex) {
		try {
			return new String(Hex.decodeHex(hex.toCharArray()));
		} catch (DecoderException e) {
			e.printStackTrace();
		}
		return null;
	}
}
