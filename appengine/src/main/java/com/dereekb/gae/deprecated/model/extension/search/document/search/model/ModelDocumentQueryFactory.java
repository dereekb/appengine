package com.thevisitcompany.gae.deprecated.model.extension.search.document.search.model;

import com.google.appengine.api.search.QueryOptions;
import com.thevisitcompany.gae.deprecated.model.extension.search.document.search.DocumentSearchRequest;
import com.thevisitcompany.gae.deprecated.model.extension.search.document.search.DocumentSearchStringConverter;
import com.thevisitcompany.gae.server.search.document.DocumentQueryBuilder;
import com.thevisitcompany.gae.server.search.document.DocumentQueryBuilderExtender;
import com.thevisitcompany.gae.server.search.document.DocumentQueryStringSanitizer;
import com.thevisitcompany.gae.server.search.document.fields.DocumentQueryLiteralField;

/**
 * Generates Document Search Requests using a ModelDocumentQuery for the type <T>.
 *
 * @author dereekb
 *
 * @param <Q>
 * @param <T>
 */
@Deprecated
public class ModelDocumentQueryFactory<T>
        implements DocumentSearchStringConverter {

	private String index;
	private Class<T> type;
	private QueryOptions options;
	private Integer defaultLimit = 20;

	private DocumentQueryBuilderExtender extender;
	private DocumentQueryStringSanitizer sanitizer;

	public ModelDocumentQueryFactory(String index, Class<T> type) {
		this.index = index;
		this.type = type;
	}

	protected void applyOptions(DocumentQueryBuilder documentQuery) {
		if (this.options != null) {
			documentQuery.setOptions(this.options);
		}
	}

	protected void applyLimit(DocumentQueryBuilder documentQuery,
	                          Integer limit) {
		if (limit == null) {
			documentQuery.setLimit(this.defaultLimit);
		} else if (limit > 0) {
			documentQuery.setLimit(limit);
		}
	}

	protected DocumentQueryBuilder createLiteralQuery(DocumentQueryBuilder query,
	                                                  String literal) {
		DocumentQueryBuilder literalQuery = query;
		String sanitizedString = literal;

		if (this.sanitizer != null) {
			sanitizedString = this.sanitizer.sanitizeQuery(sanitizedString);

			if (sanitizedString.length() > 0) {
				DocumentQueryLiteralField field = new DocumentQueryLiteralField(sanitizedString);
				literalQuery = literalQuery.and(field);
			}
		}

		return literalQuery;
	}

	@Override
	public DocumentSearchRequest makeQuery(String string) {
		return this.makeQuery(string, this.defaultLimit);
	}

	@Override
	public DocumentSearchRequest makeQuery(String string,
	                                       Integer limit) {
		ModelDocumentQuery<T> query = new ModelDocumentQuery<T>(this.type);

		DocumentQueryBuilder newQuery = query.getDocumentQuery();
		newQuery.setIndexName(this.index);

		this.applyLimit(newQuery, limit);
		this.applyOptions(newQuery);

		if (string != null && (string.isEmpty() == false)) {
			newQuery = this.createLiteralQuery(newQuery, string);
		}

		if (this.extender != null) {
			newQuery = this.extender.extendQuery(newQuery);
		}

		return newQuery;
	}

	public DocumentSearchRequest makeQueryForIdentifier(String identifier) {
		ModelDocumentQuery<T> query = new ModelDocumentQuery<T>(this.type);
		query.setIdentifier(identifier);

		DocumentQueryBuilder newQuery = query.getDocumentQuery();
		newQuery.setIndexName(this.index);
		this.applyOptions(newQuery);

		return newQuery;
	}

	public String getIndex() {
		return this.index;
	}

	public void setIndex(String index) throws NullPointerException {
		if (this.type == null) {
			throw new NullPointerException();
		}

		this.index = index;
	}

	public Class<T> getType() {
		return this.type;
	}

	public void setType(Class<T> type) throws NullPointerException {
		if (type == null) {
			throw new NullPointerException();
		}

		this.type = type;
	}

	public QueryOptions getOptions() {
		return this.options;
	}

	public void setOptions(QueryOptions options) {
		this.options = options;
	}

	public DocumentQueryStringSanitizer getSanitizer() {
		return this.sanitizer;
	}

	public void setSanitizer(DocumentQueryStringSanitizer sanitizer) {
		this.sanitizer = sanitizer;
	}

	public Integer getDefaultLimit() {
		return this.defaultLimit;
	}

	public void setDefaultLimit(Integer limit) throws IllegalArgumentException {
		if (limit == null || limit < 1) {
			throw new IllegalArgumentException("Must specify a positive, non-zero limit.");
		}

		this.defaultLimit = limit;
	}

	public DocumentQueryBuilderExtender getExtender() {
		return this.extender;
	}

	public void setExtender(DocumentQueryBuilderExtender extender) {
		this.extender = extender;
	}

}
