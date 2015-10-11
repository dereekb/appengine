package com.dereekb.gae.server.search.document;

/**
 * A simple request that retrieves a document with the given identifier from the specified Search index.
 *
 * @author dereekb
 */
@Deprecated
public class DocumentIdentifierRequest {

	private final String index;
	private final String identifier;

	private boolean rangeRequest = false;
	private boolean identifiersOnlyRequest = false;
	private Integer limit = null;

	public DocumentIdentifierRequest(String index, String identifier) {
		this.index = index;
		this.identifier = identifier;
	}

	public String getIndex() {
		return this.index;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public boolean isRangeRequest() {
		return this.rangeRequest;
	}

	public void setRangeRequest(boolean rangeRequest) {
		this.rangeRequest = rangeRequest;
	}

	public boolean isIdentifiersOnlyRequest() {
		return this.identifiersOnlyRequest;
	}

	public void setIdentifiersOnlyRequest(boolean identifiersOnlyRequest) {
		this.identifiersOnlyRequest = identifiersOnlyRequest;
	}

	public Integer getLimit() {
		return this.limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}
