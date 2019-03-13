package com.dereekb.gae.model.stored.image.set.search.document.query;

import java.util.Map;

import com.dereekb.gae.model.stored.image.set.search.document.query.StoredImageSetSearchBuilder.StoredImageSetSearch;
import com.dereekb.gae.web.api.model.extension.search.impl.model.AbstractSearchRequestBuilder;

public class StoredImageSetSearchRequestBuilder extends AbstractSearchRequestBuilder<StoredImageSetSearchRequest> {

	public StoredImageSetSearchRequestBuilder() {
		super(StoredImageSetSearchRequest.class);
	}

	@Override
    public void applyParameters(StoredImageSetSearchRequest request,
	                            Map<String, String> parameters) {
		StoredImageSetSearch search = request.getSearch();
		search.applyParameters(parameters);
	}

}
