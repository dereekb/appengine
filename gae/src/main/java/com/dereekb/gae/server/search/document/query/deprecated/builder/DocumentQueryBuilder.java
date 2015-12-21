package com.dereekb.gae.server.search.document.query.deprecated.builder;

import com.dereekb.gae.server.search.document.query.deprecated.builder.fields.DocumentQueryField;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;

/**
 * A Query that is buildable by combining other Queries together.
 *
 * Queries are immutable, and additions to them result in a new query being built.
 *
 * @author dereekb
 */
public final class DocumentQueryBuilder {

	private static final int DEFAULT_LIMIT = 20;

	/**
	 * Format to use for the formatted field.
	 *
	 * Format takes in a single string that represents the query from the fields.
	 */
	private static final String FIELD_QUERY_FORMAT = "(%s)";

	/**
	 * Format takes in three strings.
	 * 1 - Current String
	 * 2 - AND/OR/NOT
	 * 3 - Formatted Query in Link.
	 */
	private static final String LINK_QUERY_FORMAT = "%s %s (%s)";

	protected static class QueryLink {

		protected enum LinkType {
			AND("AND"),
			OR("OR"),
			NOT("NOT");

			private final String display;

			private LinkType(String display) {
				this.display = display;
			}

			@Override
            public String toString() {
				return this.display;
			}
		};

		private final LinkType type;
		private final DocumentQueryBuilder query;

		public QueryLink(DocumentQueryBuilder query, LinkType type) {
			this.query = query;
			this.type = type;
		}

		public LinkType getType() {
			return this.type;
		}

		public DocumentQueryBuilder getQuery() {
			return this.query;
		}

		public QueryLink cloneLink() {
			DocumentQueryBuilder clone = this.query.createQuery();
			QueryLink linkClone = new QueryLink(clone, this.type);
			return linkClone;
		}

		public void appendLink(QueryLink link) {
			this.query.appendLink(link);
		}

		public boolean isComplex() {
			boolean isComplex = (this.type != LinkType.AND);

			if (isComplex == false) {
				isComplex = this.query.isComplex();
			}

			return isComplex;
		}
	}

	private String indexName = null;
	private QueryLink link = null;
	private QueryOptions options;
	private DocumentQueryField fieldQuery = null;
	private Integer limit = DEFAULT_LIMIT;

	public DocumentQueryBuilder() {}

	public DocumentQueryBuilder(DocumentQueryField fieldQuery) {
		this.fieldQuery = fieldQuery;
	}

	/**
	 * Creates a new query under the specified index.
	 *
	 * @param indexName
	 *            Name of the target index.
	 */
	public DocumentQueryBuilder(String indexName) {
		this.indexName = indexName;
	}

	/**
	 * Creates a new query under the specified index with an optional fieldQuery.
	 *
	 * @param indexName
	 *            Name of the target index.
	 * @param fieldQuery
	 */
	public DocumentQueryBuilder(String indexName, DocumentQueryField fieldQuery) {
		this.fieldQuery = fieldQuery;
		this.indexName = indexName;
	}

	public String getIndexName() {
		return this.indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public DocumentQueryField getFieldQuery() {
		return this.fieldQuery;
	}

	public QueryLink getLink() {
		return this.link;
	}

	private void setLink(QueryLink link) {
		this.link = link;
	}

	public QueryOptions getOptions() {
		return this.options;
	}

	public void setOptions(QueryOptions options) {
		this.options = options;
	}

	public boolean isEmpty() {
		return (this.fieldQuery == null);
	}

	public boolean hasLink() {
		return (this.link != null);
	}

	public boolean isComplex() {
		boolean isComplex = false;

		if (this.fieldQuery != null) {
			isComplex = this.fieldQuery.isComplex();
		}

		if (this.link != null) {
			isComplex = this.link.isComplex();
		}

		return isComplex;
	}

	private void appendLink(QueryLink link) {
		if (this.link == null) {
			this.setLink(link);
		} else {
			this.link.appendLink(link);
		}
	}

	private void appendField(DocumentQueryField field) {
		if (this.fieldQuery != null) {
			DocumentQueryField appendedQuery = this.fieldQuery.appendField(field);
			this.fieldQuery = appendedQuery;
		} else {
			this.fieldQuery = field.createQueryField();
		}
	}

	/**
	 * Clones this query and fields. Does not clone the link.
	 *
	 * @return
	 */
	private DocumentQueryBuilder cloneQuery() {

		DocumentQueryField fieldQuery = null;

		if (this.fieldQuery != null) {
			fieldQuery = this.fieldQuery.createQueryField();
		}

		DocumentQueryBuilder clonedQuery = new DocumentQueryBuilder(this.indexName, fieldQuery);
		return clonedQuery;
	}

	private DocumentQueryBuilder createQuery() {

		DocumentQueryBuilder queryClone = this.cloneQuery();
		QueryLink link = null;

		if (this.link != null) {
			link = this.link.cloneLink();
		}

		queryClone.setLink(link);
		return queryClone;
	}

	/**
	 * Adds a new fieldQuery to this DocumentQuery.
	 *
	 * @param fieldQuery
	 * @return
	 */
	public DocumentQueryBuilder filter(DocumentQueryField fieldQuery) {
		DocumentQueryBuilder queryCopy = this.createQuery();
		DocumentQueryField fieldCopy = fieldQuery.createQueryField();
		queryCopy.appendField(fieldCopy);

		return queryCopy;
	}

	private DocumentQueryBuilder appendLinkWithQueryAndType(DocumentQueryBuilder query,
	                                                        QueryLink.LinkType type) {
		DocumentQueryBuilder queryCopy = this.createQuery();

		if (query.isEmpty() == false) {
			DocumentQueryBuilder inputQueryCopy = query.cloneQuery();

			QueryLink link = new QueryLink(inputQueryCopy, type);
			queryCopy.appendLink(link);
		}

		return queryCopy;
	}

	public DocumentQueryBuilder and(DocumentQueryField field) {
		DocumentQueryBuilder query = new DocumentQueryBuilder(this.indexName, field);
		return this.and(query);
	}

	public DocumentQueryBuilder and(DocumentQueryBuilder query) {
		return this.appendLinkWithQueryAndType(query, QueryLink.LinkType.AND);
	}

	public DocumentQueryBuilder or(DocumentQueryBuilder query) {
		return this.appendLinkWithQueryAndType(query, QueryLink.LinkType.OR);
	}

	public DocumentQueryBuilder not(DocumentQueryBuilder query) {
		return this.appendLinkWithQueryAndType(query, QueryLink.LinkType.NOT);
	}

	protected String getQueryString() {
		String queryString = "";

		if (this.fieldQuery != null) {
			String fieldQueryString = this.fieldQuery.toString();
			queryString = String.format(FIELD_QUERY_FORMAT, fieldQueryString);
		}

		return queryString;
	}

	@Override
    public String toString() {
		String queryString = this.getQueryString();

		if (this.link != null) {
			DocumentQueryBuilder query = this.link.getQuery();
			QueryLink.LinkType type = this.link.getType();

			String linkTypeString = type.toString();
			String linkQueryString = query.toString();

			queryString = String.format(LINK_QUERY_FORMAT, queryString, linkTypeString, linkQueryString);
		}

		return queryString;
	}

	public Query buildQuery() {
		String queryString = this.toString();
		Query.Builder queryBuilder = Query.newBuilder();

		if (this.options != null) {
			queryBuilder = queryBuilder.setOptions(this.options);
		} else {
			Integer limit = this.getLimit();
			QueryOptions options = QueryOptions.newBuilder().setLimit(limit).build();
			queryBuilder.setOptions(options);
		}

		Query query = queryBuilder.build(queryString);
		return query;
	}

	public DocumentQueryBuilder getDocumentQuery() {
		return this;
	}

    public Integer getLimit() {
		Integer limit = this.limit;

		if (limit == null) {
			if (this.options != null) {
				limit = this.options.getLimit();
			} else {
				limit = DEFAULT_LIMIT;
			}
		}

		return limit;
	}

    public void setLimit(Integer limit) throws IllegalArgumentException {
		if (limit != null && limit < 1) {
			throw new IllegalArgumentException("Must specify a positive, non-zero limit.");
		}

		this.limit = limit;
	}

}
