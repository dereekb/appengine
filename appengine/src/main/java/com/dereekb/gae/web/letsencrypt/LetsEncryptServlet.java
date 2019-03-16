package com.dereekb.gae.web.letsencrypt;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link HttpServlet} implementation used to resolve LetsEncrypt requests.
 * 
 * @author dereekb
 *
 */
public class LetsEncryptServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String REQUEST_PREFIX = "/.well-known/acme-challenge/";

	private Map<String, String> challenges;

	public LetsEncryptServlet(Map<String, String> challenges) {
		this.setChallenges(challenges);
	}

	public Map<String, String> getChallenges() {
		return this.challenges;
	}

	public void setChallenges(Map<String, String> challenges) {
		if (challenges == null) {
			throw new IllegalArgumentException("Challenges cannot be null.");
		}

		this.challenges = challenges;
	}

	// MARK: HttpServlet
	@Override
	protected void doGet(HttpServletRequest req,
	                     HttpServletResponse resp)
	        throws ServletException,
	            IOException {

		if (!req.getRequestURI().startsWith(REQUEST_PREFIX)) {
			resp.sendError(404);
			return;
		}

		String id = req.getRequestURI().substring(REQUEST_PREFIX.length());

		if (!this.challenges.containsKey(id)) {
			resp.sendError(404);
			return;
		}

		resp.setContentType("text/plain");
		resp.getOutputStream().print(this.challenges.get(id));
	}

}