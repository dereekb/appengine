package com.dereekb.gae.web.index;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * {@link HttpServlet} implementation used to silently redirect to the
 * welcome file.
 * 
 * @author dereekb
 *
 */
public class AngularHtml5Filter
        implements Filter {

	private String indexFile = "/";

	public AngularHtml5Filter() {}

	public AngularHtml5Filter(String indexFile) {
		this.setIndexFile(indexFile);
	}

	public String getIndexFile() {
		return this.indexFile;
	}

	public void setIndexFile(String indexFile) {
		if (indexFile == null || indexFile.isEmpty()) {
			throw new IllegalArgumentException("indexFile cannot be null.");
		}

		this.indexFile = indexFile;
	}

	// MARK: Filter
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request,
	                     ServletResponse response,
	                     FilterChain chain)
	        throws IOException,
	            ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		boolean isPage = !req.getRequestURI().contains(".");

		if (isPage) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(this.indexFile);
			dispatcher.forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {}

}
