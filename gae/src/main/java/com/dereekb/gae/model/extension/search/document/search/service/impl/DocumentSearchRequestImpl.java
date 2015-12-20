package com.dereekb.gae.model.extension.search.document.search.service.impl;

import com.dereekb.gae.model.extension.search.document.search.service.DocumentSearchRequest;
import com.dereekb.gae.model.extension.search.document.search.service.DocumentSearchRequestOptions;

/**
 * {@link DocumentSearchRequest} implementation.
 *
 * @author dereekb
 *
 */
public class DocumentSearchRequestImpl
        implements DocumentSearchRequest {

	private String index;
	private String expression;
	private DocumentSearchRequestOptions options;

	public DocumentSearchRequestImpl() {}

	public DocumentSearchRequestImpl(String index, String expression) {
		this.setIndex(index);
		this.setQueryExpression(expression);
	}

	public DocumentSearchRequestImpl(String index, String expression, DocumentSearchRequestOptions options) {
		this.setIndex(index);
		this.setQueryExpression(expression);
		this.setOptions(options);
	}

	@Override
	public String getIndex() {
		return this.index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	@Override
	public String getQueryExpression() {
		return this.expression;
	}

	public void setQueryExpression(String expression) {
		this.expression = expression;
	}

	@Override
	public DocumentSearchRequestOptions getOptions() {
		return this.options;
	}

	public void setOptions(DocumentSearchRequestOptions options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "DocumentSearchRequestImpl [index=" + this.index + ", expression=" + this.expression + "]";
	}

}
