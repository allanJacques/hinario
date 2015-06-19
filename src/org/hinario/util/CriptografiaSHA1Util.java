package org.hinario.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class CriptografiaSHA1Util {

	public String criptografar(final String senha) {

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			BASE64Encoder enc = new BASE64Encoder();
			byte[] senhaEmBytes = md.digest(senha.getBytes("UTF-8"));
			String senhaCrip = enc.encode(senhaEmBytes);
			return senhaCrip;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
