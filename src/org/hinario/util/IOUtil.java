package org.hinario.util;

import java.io.IOException;
import java.io.InputStream;

public class IOUtil {

	public byte[] InputStreamToArray(final InputStream is, final long tamanho) {
		byte[] returN = new byte[(int) tamanho];
		try {
			is.read(returN, 0, (int) tamanho);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returN;
	}

}
