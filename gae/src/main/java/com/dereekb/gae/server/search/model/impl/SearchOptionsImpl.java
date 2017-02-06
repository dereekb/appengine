package com.dereekb.gae.server.search.model.impl;

import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.utilities.model.search.request.MutableSearchOptions;
import com.dereekb.gae.utilities.model.search.request.SearchOptions;

/**
 * Base model query that contains a field for an Objectify query cursor and
 * limit.
 *
 * @author dereekb
 *
 */
public class SearchOptionsImpl
        implements MutableSearchOptions {

	public static final String CURSOR_PARAM = "cursor";
	public static final String LIMIT_PARAM = "limit";
	public static final String OFFSET_PARAM = "offset";

	private String cursor;
	private Integer offset;
	private Integer limit;

	public SearchOptionsImpl() {}

	public SearchOptionsImpl(SearchOptions options) {
		this.setOptions(options);
	}

	public SearchOptionsImpl(String cursor, Integer offset, Integer limit) {
		this.setCursor(cursor);
		this.setOffset(offset);
		this.setLimit(limit);
	}

	public void setOptions(SearchOptions options) {
		this.setCursor(options.getCursor());
		this.setOffset(options.getOffset());
		this.setLimit(options.getLimit());
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
		if (offset != null && offset < 0) {
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

	// MARK: Parameters
	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = new HashMap<String, String>();

		if (this.cursor != null) {
			parameters.put(CURSOR_PARAM, this.cursor);
		}

		if (this.limit != null) {
			parameters.put(LIMIT_PARAM, this.limit.toString());
		}

		if (this.offset != null) {
			parameters.put(OFFSET_PARAM, this.offset.toString());
		}

		return parameters;
	}

	// MARK: Mutable Parameters
	@Override
	public void setParameters(Map<String, String> parameters) throws IllegalArgumentException {
		parameters.get(CURSOR_PARAM);

	}

	@Override
	public String toString() {
		return "SearchOptionsImpl [cursor=" + this.cursor + ", offset=" + this.offset + ", limit=" + this.limit + "]";
	}

}
