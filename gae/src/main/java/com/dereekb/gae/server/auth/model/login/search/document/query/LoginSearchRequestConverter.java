package com.dereekb.gae.server.auth.model.login.search.document.query;

import com.dereekb.gae.model.extension.search.document.search.service.model.impl.AbstractDescribedModelDocumentRequestConverter;
import com.dereekb.gae.server.auth.model.login.search.document.query.LoginSearchBuilder.LoginSearch;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;

/**
 * Converts a {@link LoginSearchRequest} into a {@link ExpressionBuilder}.
 *
 * @author dereekb
 *
 */
public class LoginSearchRequestConverter extends AbstractDescribedModelDocumentRequestConverter<LoginSearchRequest> {

	public LoginSearchRequestConverter(String index) throws IllegalArgumentException {
		super(index);
	}

	// MARK: AbstractDescribedModelDocumentRequest
	@Override
	public ExpressionBuilder buildExpression(LoginSearchRequest request) {
		ExpressionBuilder builder = super.buildExpression(request);

		LoginSearch search = request.getSearch();
		builder = builder.and(search.makeExpression());

		return builder;
	}

	@Override
	public String toString() {
		return "LoginSearchRequestConverter []";
	}

}
