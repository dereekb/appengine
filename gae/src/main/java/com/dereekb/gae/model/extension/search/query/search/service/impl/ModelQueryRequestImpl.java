package com.dereekb.gae.model.extension.search.query.search.service.impl;

import java.util.Map;

import com.dereekb.gae.model.extension.search.query.search.service.ModelQueryRequest;
import com.dereekb.gae.server.search.model.SearchOptions;
import com.dereekb.gae.server.search.model.impl.SearchOptionsImpl;

/**
 * {@link ModelQueryRequest} implementation.
 *
 * @author dereekb
 *
 */
public class ModelQueryRequestImpl extends SearchOptionsImpl
        implements ModelQueryRequest {

	public boolean keySearch;
	public Map<String, String> parameters;

	public ModelQueryRequestImpl() {}

	public ModelQueryRequestImpl(boolean keySearch, Map<String, String> parameters) {
		this.setKeySearch(keySearch);
		this.setParameters(parameters);
	}

	public ModelQueryRequestImpl(boolean keySearch, SearchOptions options, Map<String, String> parameters) {
		super(options);
		this.setKeySearch(keySearch);
		this.setParameters(parameters);
	}

	@Override
	public boolean isKeySearch() {
		return this.keySearch;
	}

	public void setKeySearch(boolean keySearch) {
		this.keySearch = keySearch;
	}

	@Override
	public Map<String, String> getParameters() {
		return this.parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "ModelQueryRequestImpl [keySearch=" + this.keySearch + ", parameters=" + this.parameters + ", cursor="
		        + this.cursor + ", offset=" + this.offset + ", limit=" + this.limit + "]";
	}

}
