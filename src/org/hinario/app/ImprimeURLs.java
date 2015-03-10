package org.hinario.app;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("*")
public class ImprimeURLs implements Filter {

	private final static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.ss");

	public ImprimeURLs() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		boolean imprimeParametrosDeRequisicao = true;
		boolean imprimeAtributosDeSessao = false;
		request.setCharacterEncoding("UTF-8");
		// response.setCharacterEncoding("UTF-8");

		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		System.out.println(sdf.format(new Date()) + "\t" + ((HttpServletRequest) request).getRequestURL() + "\t" + ((HttpServletRequest) request).getMethod());
		if (imprimeParametrosDeRequisicao) {
			Enumeration<String> enumRequest = request.getParameterNames();
			while (enumRequest.hasMoreElements()) {
				String nomeDoParametro = enumRequest.nextElement();
				System.out.println(nomeDoParametro + "=" + request.getAttribute(nomeDoParametro));
			}
		}

		if (imprimeAtributosDeSessao) {
			Enumeration<String> enumSession = ((HttpServletRequest) request).getSession().getAttributeNames();
			while (enumSession.hasMoreElements()) {
				String nomeDoAtributo = enumSession.nextElement();
				System.out.println(nomeDoAtributo + "=" + ((HttpServletRequest) request).getSession().getAttribute(nomeDoAtributo));
			}
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
