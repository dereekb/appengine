package com.dereekb.gae.server.auth.model.login.search.document.query;

import java.util.Map;

import com.dereekb.gae.server.auth.model.login.deprecated.search.document.query.LoginSearchBuilder.LoginSearch;
import com.dereekb.gae.web.api.model.extension.search.impl.model.AbstractDescribedSearchRequestBuilder;


public class LoginSearchRequestBuilder extends AbstractDescribedSearchRequestBuilder<LoginSearchRequest> {

	protected LoginSearchRequestBuilder() {
		super(LoginSearchRequest.class);
	}

	@Override
	public void applyNonDescriptorParameters(LoginSearchRequest request,
	                            Map<String, String> parameters) {
		LoginSearch search = request.getSearch();
		search.applyParameters(parameters);
	}

}
