package org.hinario.util.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hinario.dao.UsuarioDAO;
import org.hinario.model.Usuario;
import org.hinario.util.NumberUtil;

@WebServlet(name = "ImagemServlet", urlPatterns = "/imagemstream/usuario/*")
public class ImagemStream extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ImagemStream() {
		super();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String requestString = request.getRequestURL().toString();
			String[] urlParts = requestString.split("/");
			int tamanhoParts = urlParts.length;
			if (!(tamanhoParts < 7) || NumberUtil.isNumber(urlParts[tamanhoParts - 1])) {
				Long id = this.getId(urlParts[tamanhoParts - 1]);

				UsuarioDAO usuarioDao = new UsuarioDAO();
				Usuario usuario = usuarioDao.getUsuarioPorId(id);
				if (usuario != null && usuario.getImagem() != null) {
					for (byte byteTemp : usuario.getImagem()) {
						response.getOutputStream().write(byteTemp);
					}
				}
			}
			response.getOutputStream().close();
		} catch (NumberFormatException e) {
			// e.printStackTrace();
		}
	}

	private Long getId(final String stringID) {
		StringBuilder sb = new StringBuilder("");
		for (char byteTemp : stringID.toCharArray()) {
			if (Character.isDigit(byteTemp)) {
				sb.append(byteTemp);
			} else {
				return Long.parseLong(sb.toString());
			}
		}
		return Long.parseLong(sb.toString());
	}
}
