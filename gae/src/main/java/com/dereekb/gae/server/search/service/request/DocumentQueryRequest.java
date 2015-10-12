package com.dereekb.gae.server.search.service.request;

import com.google.appengine.api.search.Query;

public interface DocumentQueryRequest
        extends SearchDocumentRequest {

	public Query getDocumentQuery();

}
