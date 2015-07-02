package com.dereekb.gae.model.extension.search.document.search.service;

import java.util.List;

import com.google.appengine.api.search.ScoredDocument;

/**
 * Low-level service for reading {@link ScoredDocument}s using a custom query.
 *
 * @author dereekb
 *
 * @param <Q>
 */
public interface DocumentSearchService<Q> {

	public List<ScoredDocument> search(Q query);

}
