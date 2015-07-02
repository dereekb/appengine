package com.dereekb.gae.server.search.document;

/**
 * A simple request that retrieves a document with the given identifier from the specified Search index.
 * 
 * @author dereekb
 */
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
		return index;
	}

	public String getIdentifier() {
		return identifier;
	}

	public boolean isRangeRequest() {
		return rangeRequest;
	}

	public void setRangeRequest(boolean rangeRequest) {
		this.rangeRequest = rangeRequest;
	}

	public boolean isIdentifiersOnlyRequest() {
		return identifiersOnlyRequest;
	}

	public void setIdentifiersOnlyRequest(boolean identifiersOnlyRequest) {
		this.identifiersOnlyRequest = identifiersOnlyRequest;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}
