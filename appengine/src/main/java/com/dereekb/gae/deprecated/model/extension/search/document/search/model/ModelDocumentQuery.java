package com.thevisitcompany.gae.deprecated.model.extension.search.document.search.model;

import com.thevisitcompany.gae.deprecated.model.extension.search.document.search.DocumentSearchRequest;
import com.thevisitcompany.gae.model.extension.search.document.deprecated.ModelDocument;
import com.thevisitcompany.gae.server.search.document.DocumentQueryBuilder;
import com.thevisitcompany.gae.server.search.document.fields.DocumentQueryAtomField;
import com.thevisitcompany.gae.server.search.document.fields.DocumentQueryField;

/**
 * A basic query that performs a global search on the target index with the given type.
 *
 * An optional identifier can be added to narrow the search.
 *
 * @author dereekb
 */
@Deprecated
public class ModelDocumentQuery<T>
        implements DocumentSearchRequest {

	private Integer limit;
	private String identifier;
	private final String type;

	public ModelDocumentQuery(String type) {
		this.type = type;
	}

	public ModelDocumentQuery(Class<T> type) {
		this.type = type.getSimpleName();
	}

	/**
	 * Builds the document query to be used. Cannot return null.
	 *
	 * @return
	 */
	protected DocumentQueryBuilder buildDocumentQuery() {
		DocumentQueryField typeField = new DocumentQueryAtomField(ModelDocument.MODEL_TYPE_KEY, this.type);
		DocumentQueryBuilder baseQuery = new DocumentQueryBuilder(typeField);

		if (this.identifier != null) {
			DocumentQueryField idField = new DocumentQueryAtomField(ModelDocument.MODEL_IDENTIFIER_KEY, this.identifier);
			baseQuery = baseQuery.and(idField);
		}

		if (this.limit != null) {
			baseQuery.setLimit(this.limit);
		}

		return baseQuery;
	}

	@Override
	public DocumentQueryBuilder getDocumentQuery() {
		DocumentQueryBuilder documentQuery = this.buildDocumentQuery();
		return documentQuery;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getType() {
		return this.type;
	}

	@Override
    public Integer getLimit() {
		return this.limit;
	}

	@Override
    public void setLimit(Integer limit) throws IllegalArgumentException {
		if (limit != null && limit < 1) {
			throw new IllegalArgumentException("Must specify a positive, non-zero limit.");
		}

		this.limit = limit;
	}

}
