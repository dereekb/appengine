package com.dereekb.gae.model.extension.search.document.search.service.model;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.search.system.response.SearchDocumentQueryResponse;
import com.dereekb.gae.utilities.model.search.response.ModelSearchResponse;

/**
 * Contains {@link ModelKey} search results and model returns.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelDocumentSearchResponse<T>
        extends ModelSearchResponse<T>, SearchDocumentQueryResponse {

}
