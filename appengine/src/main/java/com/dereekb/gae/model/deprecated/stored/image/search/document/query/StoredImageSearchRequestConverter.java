package com.dereekb.gae.model.stored.image.search.document.query;

import com.dereekb.gae.model.deprecated.stored.image.search.document.query.StoredImageSearchBuilder.StoredImageSearch;
import com.dereekb.gae.model.extension.deprecated.search.document.search.service.model.impl.AbstractModelDocumentRequestConverter;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;


public class StoredImageSearchRequestConverter extends AbstractModelDocumentRequestConverter<StoredImageSearchRequest> {

	public StoredImageSearchRequestConverter(String index) throws IllegalArgumentException {
		super(index);
	}

	// MARK: AbstractModelDocumentRequest
	@Override
	public ExpressionBuilder buildExpression(StoredImageSearchRequest request) {
		StoredImageSearch search = request.getSearch();
		ExpressionBuilder builder = search.makeExpression();
		return builder;
	}

	@Override
	public String toString() {
		return "StoredImageSearchRequestConverter []";
	}

}
