package com.dereekb.gae.model.stored.image.set.search.document.query;

import com.dereekb.gae.model.extension.search.document.search.service.model.impl.AbstractModelDocumentRequestConverter;
import com.dereekb.gae.model.stored.image.set.search.document.query.StoredImageSetSearchBuilder.StoredImageSetSearch;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;

public class StoredImageSetSearchRequestConverter extends AbstractModelDocumentRequestConverter<StoredImageSetSearchRequest> {

	public StoredImageSetSearchRequestConverter(String index) throws IllegalArgumentException {
		super(index);
	}

	// MARK: AbstractModelDocumentRequest
	@Override
	public ExpressionBuilder buildExpression(StoredImageSetSearchRequest request) {
		StoredImageSetSearch search = request.getSearch();
		ExpressionBuilder builder = search.makeExpression();
		return builder;
	}

	@Override
	public String toString() {
		return "StoredImageSetSearchRequestConverter []";
	}

}
