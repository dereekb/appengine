package com.dereekb.gae.server.auth.security.login.oauth.impl.service;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.LoginPointerService;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthInsufficientException;
import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResult;

/**
 * {@link OAuthLoginServiceImpl} extension that adds a {@link Filter} that
 * triggers an {@link OAuthInsufficientException}.
 *
 * @author dereekb
 *
 */
public class FilteredOAuthLoginServiceImpl extends OAuthLoginServiceImpl {

	private static final String DEFAULT_MESSAGE = "OAuth credentials were rejected by the server.";

	private Filter<OAuthAuthorizationInfo> filter;
	private String filterMessage = DEFAULT_MESSAGE;

	public FilteredOAuthLoginServiceImpl(LoginPointerService loginPointerService,
	        Filter<OAuthAuthorizationInfo> filter) {
		super(loginPointerService);
		this.setFilter(filter);
	}

	public Filter<OAuthAuthorizationInfo> getFilter() {
		return this.filter;
	}

	public void setFilter(Filter<OAuthAuthorizationInfo> filter) throws IllegalArgumentException {
		if (filter == null) {
			throw new IllegalArgumentException("Filter cannot be null.");
		}

		this.filter = filter;
	}

	public String getFilterMessage() {
		return this.filterMessage;
	}

	public void setFilterMessage(String filterMessage) {
		if (filterMessage == null) {
			filterMessage = DEFAULT_MESSAGE;
		}

		this.filterMessage = filterMessage;
	}

	// MARK: OAuthLoginServiceImpl
	@Override
	public LoginPointer login(OAuthAuthorizationInfo authCode) throws OAuthInsufficientException {
		FilterResult result = this.filter.filterObject(authCode);

		if (result != FilterResult.PASS) {
			throw new OAuthInsufficientException(this.filterMessage);
		}

		return super.login(authCode);
	}

	@Override
	public String toString() {
		return "FilteredOAuthLoginServiceImpl [filter=" + this.filter + ", filterMessage=" + this.filterMessage + "]";
	}

}
