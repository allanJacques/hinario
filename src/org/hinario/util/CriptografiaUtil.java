package org.hinario.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import org.hinario.app.AppMessage;

public class CriptografiaUtil {

	private Cipher crip;
	private Cipher deCrip;
	private final StringUtil stringUtil;

	public CriptografiaUtil(final String chave) {
		this.stringUtil = new StringUtil();
		try {
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(chave.getBytes("UTF16"));

			KeyGenerator keyGenerator = KeyGenerator.getInstance("DESEDE");
			keyGenerator.init(secureRandom);

			Key key = keyGenerator.generateKey();

			this.crip = Cipher.getInstance("DESEDE/ECB/PKCS5Padding");
			crip.init(Cipher.ENCRYPT_MODE, key);

			this.deCrip = Cipher.getInstance("DESEDE/ECB/PKCS5Padding");
			deCrip.init(Cipher.DECRYPT_MODE, key);

		} catch (NoSuchAlgorithmException | UnsupportedEncodingException | NoSuchPaddingException | InvalidKeyException e) {
			e.printStackTrace();
		}
	}

	public String criptografar(final String valor) {
		try {
			// byte[] bytesPraCriptografar = valor.getBytes("UTF8");
			// byte[] bytesCriptografados =
			// this.crip.doFinal(bytesPraCriptografar);
			// String stringCriptografada =
			// this.StringToByteArray(bytesCriptografados);
			// String stringCriptografadaHex =
			// this.stringUtil.StringToHex(stringCriptografada);
			// return stringCriptografadaHex;

			return this.stringUtil.StringToHex(this.StringToByteArray(this.crip.doFinal(valor.getBytes("UTF8"))));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public String descriptografar(final String valor) {
		try {
			return new String(this.deCrip.doFinal(this.ByteArrayToString(this.stringUtil.HexToString(valor))), "UTF8");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	private String StringToByteArray(final byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 4);
		DecimalFormat df = new DecimalFormat("000");
		for (byte bTemp : bytes) {
			sb.append(df.format(Math.abs(bTemp)));
		}
		for (byte bTemp : bytes) {
			sb.append(bTemp >= 0 ? '+' : '-');
		}
		return sb.toString();
	}

	private byte[] ByteArrayToString(final String bytes) {
		if (bytes.length() % 4 != 0) {
			new InvalidParameterException(AppMessage.getStaticString("message.tamanhoStringInvalido"));
		}
		byte[] retorno = new byte[(bytes.length() / 4)];
		for (int i = 0; i < (bytes.length() / 4) * 3; i += 3) {
			byte numero = Byte.parseByte(bytes.substring(i, i + 3));
			byte fator = (byte) (bytes.charAt(((retorno.length * 3) + (i / 3))) == '+' ? 1 : -1);
			retorno[i / 3] = (byte) (numero * fator);
		}
		return retorno;
	}
}
