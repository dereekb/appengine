package com.dereekb.gae.model.extension.search.document.search.service.key;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Service for searching {@link ModelKey} using a custom query.
 *
 * @author dereekb
 */
public interface KeyDocumentSearchService<Q> {

	public List<ModelKey> searchKeys(Q query);

}
