package com.dereekb.gae.server.auth.model.login.search.document.query;

import com.dereekb.gae.model.extension.search.document.search.service.model.impl.AbstractDescribedModelDocumentRequest;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.search.document.query.LoginSearchBuilder.LoginSearch;

/**
 * Search request for a {@link Login}.
 *
 * @author dereekb
 *
 */
public class LoginSearchRequest extends AbstractDescribedModelDocumentRequest {

	public static final LoginSearchBuilder BUILDER = new LoginSearchBuilder("");

	private LoginSearch search;

	public LoginSearchRequest() {
		this.search = BUILDER.make();
	}

	public LoginSearch getSearch() {
		return this.search;
	}

	public void setSearch(LoginSearch search) throws IllegalArgumentException {
		if (search == null) {
			throw new IllegalArgumentException("Search cannot be null.");
		}

		this.search = search;
	}

	@Override
	public String toString() {
		return "LoginSearchRequest [search=" + this.search + ", descriptor=" + this.descriptor + "]";
	}

}
