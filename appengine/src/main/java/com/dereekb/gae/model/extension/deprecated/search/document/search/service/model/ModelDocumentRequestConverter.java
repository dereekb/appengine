package com.dereekb.gae.model.extension.search.document.search.service.model;

import com.dereekb.gae.model.extension.deprecated.search.document.search.service.DocumentSearchRequest;

/**
 * Used for building a {@link DocumentSearchRequest} from a model document
 * request.
 *
 * @author dereekb
 *
 * @param <Q>
 *            query type
 */
public interface ModelDocumentRequestConverter<Q> {

	public DocumentSearchRequest buildSearchRequest(Q request);

}
