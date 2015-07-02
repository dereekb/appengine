package com.dereekb.gae.model.extension.search.document.search.service.model;

import java.util.List;

import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Contains {@link ModelKey} search results and model returns.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface ModelDocumentSearchResponse<T extends UniqueModel>
        extends ReadResponse<T> {

	public List<ModelKey> getKeySearchResults();

}
