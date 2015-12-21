package com.dereekb.gae.model.extension.search.document.search.components;

import java.util.List;

import com.dereekb.gae.server.search.document.query.deprecated.builder.DocumentQueryBuilder;
import com.google.appengine.api.search.ScoredDocument;

/**
 * Used for retrieving documents from the Search API.
 *
 * @author dereekb
 */
public interface DocumentSearchResource {

	public List<ScoredDocument> search(DocumentQueryBuilder builder);

}
