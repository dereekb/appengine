package com.dereekb.gae.model.extension.search.query.search.service;

import java.util.Map;

import com.dereekb.gae.server.search.model.SearchOptions;

/**
 * Request used by the {@link ModelQueryService}.
 *
 * @author dereekb
 *
 */
public interface ModelQueryRequest
        extends SearchOptions {

	public boolean isKeySearch();

	public Map<String, String> getParameters();

}
