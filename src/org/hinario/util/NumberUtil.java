package org.hinario.util;

public class NumberUtil {

	public static boolean isNumber(final Object trY) {
		try {
			Long.parseLong(trY.toString());
			return true;
		} catch (NumberFormatException nfe) {
			// por ser um teste nao logamos o possivel erro
		}
		return false;
	}
}
