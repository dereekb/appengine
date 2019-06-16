package com.dereekb.gae.model.extension.search.document.search.service.model.impl;

import com.dereekb.gae.model.extension.deprecated.search.document.search.service.DocumentSearchRequest;
import com.dereekb.gae.model.extension.deprecated.search.document.search.service.impl.DocumentSearchRequestImpl;
import com.dereekb.gae.model.extension.deprecated.search.document.search.service.model.ModelDocumentRequestConverter;
import com.dereekb.gae.server.deprecated.search.document.query.expression.Expression;

/**
 * Abstract {@link ModelDocumentRequestConverter} implementation.
 *
 * @author dereekb
 *
 * @param <Q>
 *            request type
 */
public abstract class AbstractModelDocumentRequestConverter<Q extends AbstractModelDocumentRequest>
        implements ModelDocumentRequestConverter<Q> {

	private String index;

	public AbstractModelDocumentRequestConverter(String index) throws IllegalArgumentException {
		this.setIndex(index);
	}

	public String getIndex() {
		return this.index;
	}

	public void setIndex(String index) throws IllegalArgumentException {
		if (index == null) {
			throw new IllegalArgumentException("Index cannot be null.");
		}

		this.index = index;
	}

	public abstract Expression buildExpression(Q request);

	// MARK: ModelDocumentRequestConverter
	@Override
	public DocumentSearchRequest buildSearchRequest(Q request) {
		DocumentSearchRequestImpl searchRequest = new DocumentSearchRequestImpl();
		Expression expression = request.getOverride();

		if (expression == null) {
			expression = this.buildExpression(request);
		}

		searchRequest.setQueryExpression(expression);
		searchRequest.setIndex(this.index);
		searchRequest.setOptions(request);

		return searchRequest;
	}

}
