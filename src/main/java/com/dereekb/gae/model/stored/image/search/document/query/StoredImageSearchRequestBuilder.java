package com.dereekb.gae.model.stored.image.search.document.query;

import java.util.Map;

import com.dereekb.gae.model.stored.image.search.document.query.StoredImageSearchBuilder.StoredImageSearch;
import com.dereekb.gae.web.api.model.extension.search.impl.model.AbstractSearchRequestBuilder;


public class StoredImageSearchRequestBuilder extends AbstractSearchRequestBuilder<StoredImageSearchRequest> {

	public StoredImageSearchRequestBuilder() {
		super(StoredImageSearchRequest.class);
	}

	@Override
	public void applyParameters(StoredImageSearchRequest request,
	                            Map<String, String> parameters) {
		StoredImageSearch search = request.getSearch();
		search.applyParameters(parameters);
	}

}
