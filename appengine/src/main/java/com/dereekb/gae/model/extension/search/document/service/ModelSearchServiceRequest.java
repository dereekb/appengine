package com.dereekb.gae.model.extension.search.document.service;

import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.server.search.request.SearchServiceQueryRequest;

/**
 * Request used by the {@link ModelSearchService}.
 *
 * @author dereekb
 *
 */
public interface ModelSearchServiceRequest
        extends SearchServiceQueryRequest, TypedModel {}
