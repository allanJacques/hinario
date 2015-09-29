package org.hinario.util.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hinario.dao.CanticoDAO;
import org.hinario.model.Arquivo;

@WebServlet("/arquivostreamview/*")
public class ArquivoStreamView extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ArquivoStreamView() {
		super();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			CanticoDAO dao = new CanticoDAO();
			Arquivo arquivo = dao.getArquivo(id);
			OutputStream out = response.getOutputStream();

			response.setContentLengthLong(arquivo.getTamanho());
			response.setContentType(arquivo.getMimeType());
			for (byte b : arquivo.getConteudo()) {
				out.write(b);
			}
			out.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
}
