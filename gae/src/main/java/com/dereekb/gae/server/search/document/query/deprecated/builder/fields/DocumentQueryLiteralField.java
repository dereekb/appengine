package com.dereekb.gae.server.search.document.query.deprecated.builder.fields;

/**
 * Used for easily defining a global query, or an arbitary query.
 * @author dereekb
 *
 */
public final class DocumentQueryLiteralField extends DocumentQueryField {

	private final String query;
	
	public DocumentQueryLiteralField(String query) {
		super(null);
		this.query = query;
	}
	
	public DocumentQueryLiteralField(String query, Boolean not) {
		super(null, not);
		this.query = query;
	}
	
	@Override
	protected DocumentQueryField cloneQuery() {
		DocumentQueryLiteralField copy = new DocumentQueryLiteralField(this.query, this.isNot());
		return copy;
	}

	@Override
	protected String getQueryString() {
		return this.query;
	}

}
