package org.hinario.app;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("*")
public class ImprimeURLs implements Filter {

	private final static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.ss");

	public ImprimeURLs() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println(sdf.format(new Date()) + "\t" + ((HttpServletRequest) request).getRequestURL() + "\t" + ((HttpServletRequest) request).getMethod());
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
