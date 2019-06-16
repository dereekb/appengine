package com.dereekb.gae.model.stored.blob.search.document.query;

import com.dereekb.gae.model.deprecated.stored.blob.search.document.query.StoredBlobSearchBuilder.StoredBlobSearch;
import com.dereekb.gae.model.extension.deprecated.search.document.search.service.model.impl.AbstractDescribedModelDocumentRequestConverter;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;

/**
 * Converts a {@link StoredBlobSearchRequest} into a {@link ExpressionBuilder}.
 *
 * @author dereekb
 *
 */
public class StoredBlobSearchRequestConverter extends AbstractDescribedModelDocumentRequestConverter<StoredBlobSearchRequest> {

	public StoredBlobSearchRequestConverter(String index) throws IllegalArgumentException {
		super(index);
	}

	// MARK: AbstractDescribedModelDocumentRequest
	@Override
	public ExpressionBuilder buildExpression(StoredBlobSearchRequest request) {
		ExpressionBuilder builder = super.buildExpression(request);

		StoredBlobSearch search = request.getSearch();
		builder = builder.and(search.makeExpression());

		return builder;
	}

	@Override
	public String toString() {
		return "StoredBlobSearchRequestConverter []";
	}

}
