package com.dereekb.gae.server.search.model.impl;

import com.dereekb.gae.server.search.model.MutableSearchOptions;


/**
 * Base model query that contains a field for an Objectify query cursor and
 * limit.
 *
 * @author dereekb
 *
 */
public class SearchOptionsImpl
        implements MutableSearchOptions {

	private static final Integer DEFAULT_LIMIT = 20;

	protected String cursor;
	protected Integer offset = 0;
	protected Integer limit = DEFAULT_LIMIT;

	public SearchOptionsImpl() {}

	public SearchOptionsImpl(String cursor, Integer offset, Integer limit) {
		this.setCursor(cursor);
		this.setOffset(offset);
		this.setLimit(limit);
	}

	@Override
	public String getCursor() {
		return this.cursor;
	}

	@Override
	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	@Override
	public Integer getOffset() {
		return this.offset;
	}

	@Override
	public void setOffset(Integer offset) {
		if (offset != null && offset < 1) {
			throw new IllegalArgumentException("Must specify a positive, non-zero offset.");
		}

		this.offset = offset;
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

	@Override
	public String toString() {
		return "SearchOptionsImpl [cursor=" + this.cursor + ", offset=" + this.offset + ", limit=" + this.limit + "]";
	}

}
