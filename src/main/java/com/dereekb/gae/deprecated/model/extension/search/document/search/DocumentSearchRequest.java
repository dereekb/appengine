package com.thevisitcompany.gae.deprecated.model.extension.search.document.search;

import com.thevisitcompany.gae.server.search.document.DocumentQueryBuilder;

@Deprecated
public interface DocumentSearchRequest {

	public abstract DocumentQueryBuilder getDocumentQuery();

	public Integer getLimit();

	/**
	 * Sets the limit of items to return.
	 *
	 * @param limit
	 * @throws IllegalArgumentException Thrown if the limit is negative or zero.
	 */
	public void setLimit(Integer limit) throws IllegalArgumentException;

}
