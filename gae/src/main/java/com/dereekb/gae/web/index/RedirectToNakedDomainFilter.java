package com.dereekb.gae.web.index;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter used for redirecting www, and any other domains to the naked domain.
 * 
 * @author dereekb
 *
 */
public class RedirectToNakedDomainFilter
        implements Filter {

	public static final String SPLITTER = "\\.";

	private int statusCode = HttpServletResponse.SC_MOVED_PERMANENTLY;
	private String redirectUrl;

	public RedirectToNakedDomainFilter(String redirectUrl) {
		this.setRedirectUrl(redirectUrl);
	}

	public String getRedirectUrl() {
		return this.redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		if (redirectUrl == null || redirectUrl.isEmpty()) {
			throw new IllegalArgumentException("redirectUrl cannot be null.");
		}

		this.redirectUrl = redirectUrl;
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
		HttpServletResponse resp = (HttpServletResponse) response;

		// Redirect all non-naked to the redirect url.
		if (this.shouldRedirect(req)) {
			resp.setStatus(this.statusCode);
			resp.setHeader("Location", this.redirectUrl);
			return;
		}

		chain.doFilter(request, response);
	}

	protected boolean shouldRedirect(HttpServletRequest req) {
		String serverName = this.getRequestDomainName(req);
		String[] splits = serverName.split(SPLITTER, 3);	// www, google.com
		return splits.length > 2;	// Triggers on everything except google.com -> "google", "com"
	}
	
	/*
	protected String getRequestSubdomain(HttpServletRequest req) {
		String serverName = this.getRequestDomainName(req);
		String[] splits = serverName.split(SPLITTER);
		
		if (splits.length > 2) {
			
		} else {
			return null;
		}
	}
	*/

	protected String getRequestDomainName(HttpServletRequest req) {
		String domain;

		try {
			domain = new URI(req.getRequestURL().toString()).getHost();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

		return domain;
	}

	/*
	 * protected String makeRedirectUrl(HttpServletRequest req) {
	 * String redirectDomain = this.redirectUrl;
	 * String requestUri = req.getRequestURI();
	 * 
	 * String basePath = PathUtility.buildPath(redirectDomain, requestUri);
	 * 
	 * }
	 */

	@Override
	public void destroy() {}

}
