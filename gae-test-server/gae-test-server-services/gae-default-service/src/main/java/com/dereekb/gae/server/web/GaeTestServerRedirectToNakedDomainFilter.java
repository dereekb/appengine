package com.dereekb.gae.server.web;

import com.dereekb.gae.web.index.AllowedRedirectToNakedDomainFilter;

/**
 * Used to redirect all requests from www.dereekb.com to the naked domain name.
 *
 * @author dereekb
 *
 */
public class GaeTestServerRedirectToNakedDomainFilter extends AllowedRedirectToNakedDomainFilter {

	public static final String DOMAIN_NAME = "https://dereekb.com/";

	public GaeTestServerRedirectToNakedDomainFilter() {
		super(DOMAIN_NAME);
	}

}
