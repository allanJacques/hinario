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
		if (true) {
			Enumeration<String> enumRequest = request.getParameterNames();
			while (enumRequest.hasMoreElements()) {
				String parameterName = enumRequest.nextElement();
				System.out.println(parameterName + "=" + request.getAttribute(parameterName));
			}
		}

		if (true) {
			Enumeration<String> enumSession = ((HttpServletRequest) request).getSession().getAttributeNames();
			while (enumSession.hasMoreElements()) {
				String parameterName = enumSession.nextElement();
				System.out.println(parameterName + "=" + ((HttpServletRequest) request).getSession().getAttribute(parameterName));
			}
		}
		System.out.println(sdf.format(new Date()) + "\t" + ((HttpServletRequest) request).getRequestURL() + "\t" + ((HttpServletRequest) request).getMethod());
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
