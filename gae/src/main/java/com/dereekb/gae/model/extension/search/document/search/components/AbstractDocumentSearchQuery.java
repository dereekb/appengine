package com.dereekb.gae.model.extension.search.document.search.components;

/**
 * Abstract implementation of {@link DocumentSearchQuery}.
 *
 * @author dereekb
 */
public abstract class AbstractDocumentSearchQuery
        implements DocumentSearchQuery {

	private Integer limit;

	public AbstractDocumentSearchQuery() {}

	public AbstractDocumentSearchQuery(Integer limit) {
		this.limit = limit;
	}

	@Override
	public Integer getLimit() {
    	return this.limit;
    }

	public void setLimit(Integer limit) {
    	this.limit = limit;
    }

}
