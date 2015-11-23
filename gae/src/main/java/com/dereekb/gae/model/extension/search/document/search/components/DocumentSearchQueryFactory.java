package com.dereekb.gae.model.extension.search.document.search.components;

import com.dereekb.gae.model.extension.search.document.index.component.builder.impl.staged.initializer.impl.ModelStagedDocumentBuilderInitializer;
import com.dereekb.gae.server.search.document.query.builder.DocumentQueryBuilder;
import com.dereekb.gae.server.search.document.query.builder.fields.DocumentQueryAtomField;
import com.dereekb.gae.server.search.document.query.builder.fields.DocumentQueryField;

public final class DocumentSearchQueryFactory<Q extends DocumentSearchQuery>
        implements DocumentSearchQueryConverter<Q> {

	private final String type;
	private final DocumentSearchQueryFactoryDelegate<Q> delegate;

	public DocumentSearchQueryFactory(Class<?> type, DocumentSearchQueryFactoryDelegate<Q> delegate) {
		this.type = type.getSimpleName();
		this.delegate = delegate;
	}

	public DocumentSearchQueryFactory(String type, DocumentSearchQueryFactoryDelegate<Q> delegate) {
		this.type = type;
		this.delegate = delegate;
	}

	public String getType() {
		return this.type;
	}

	public DocumentSearchQueryFactoryDelegate<Q> getDelegate() {
		return this.delegate;
	}

	@Override
    public DocumentQueryBuilder convertQuery(Q query) {
		DocumentQueryField typeField = new DocumentQueryAtomField(ModelStagedDocumentBuilderInitializer.MODEL_TYPE_FIELD_KEY,
		        this.type);
		DocumentQueryBuilder baseQuery = new DocumentQueryBuilder(typeField);

		Integer limit = query.getLimit();

		if (limit != null) {
			baseQuery.setLimit(limit);
		}

		DocumentQueryBuilder finalQuery = this.delegate.buildWithQuery(baseQuery, query);
		return finalQuery;
	}

}
