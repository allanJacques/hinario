package org.hinario.app;

import java.io.IOException;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hinario.managedbean.UsuarioLoginBean;

@WebFilter("*")
public class LoginFilter implements Filter {

	public LoginFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String path = new URL(((HttpServletRequest) request).getRequestURL().toString()).getPath().replaceAll("^/", "").replaceAll("/$", "").replaceAll("[;]jsessionid=.*", "");
		if ((!path.endsWith("login.jsf") && (!path.endsWith(".gif.jsf") && !path.endsWith(".css.jsf") && !path.endsWith(".js.jsf") && !path.endsWith(".png.jsf"))) && (path.endsWith(".jsf") || path.endsWith(".xhtml"))) {
			UsuarioLoginBean loginBean = (UsuarioLoginBean) ((HttpServletRequest) request).getSession().getAttribute("usuarioLoginBean");
			if (loginBean == null || (loginBean != null && !loginBean.isLogado())) {
				((HttpServletResponse) response).sendRedirect("login.jsf");
			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
