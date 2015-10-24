package com.dereekb.gae.server.search.document.query.builder.impl;

import java.util.List;

import com.dereekb.gae.server.search.document.query.builder.DocumentQueryBuilder;
import com.dereekb.gae.server.search.document.query.builder.DocumentQueryBuilderExtender;
import com.dereekb.gae.server.search.document.query.builder.fields.DocumentQueryLiteralField;

public class DefaultDocumentQueryExtender
        implements DocumentQueryBuilderExtender {

	private List<String> andLiterals;

	protected DocumentQueryBuilder extendWithAnds(DocumentQueryBuilder builder) {
		DocumentQueryBuilder newBuilder = builder;

		if ((this.andLiterals != null) && (this.andLiterals.isEmpty() == false)) {
			for (String literal : this.andLiterals) {
				DocumentQueryLiteralField field = new DocumentQueryLiteralField(literal);
				newBuilder = newBuilder.and(field);
			}
		}

		return newBuilder;
	}

	@Override
	public DocumentQueryBuilder extendQuery(DocumentQueryBuilder builder) {
		return this.extendWithAnds(builder);
	}

	public List<String> getAndLiterals() {
		return andLiterals;
	}

	public void setAndLiterals(List<String> andLiterals) {
		this.andLiterals = andLiterals;
	}

}
