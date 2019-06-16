package com.dereekb.gae.model.stored.blob.search.document.query;

import java.util.Map;

import com.dereekb.gae.model.deprecated.stored.blob.search.document.query.StoredBlobSearchBuilder.StoredBlobSearch;
import com.dereekb.gae.web.api.model.extension.search.impl.model.AbstractDescribedSearchRequestBuilder;


public class StoredBlobSearchRequestBuilder extends AbstractDescribedSearchRequestBuilder<StoredBlobSearchRequest> {

	public StoredBlobSearchRequestBuilder() {
		super(StoredBlobSearchRequest.class);
	}

	@Override
	public void applyNonDescriptorParameters(StoredBlobSearchRequest request,
	                            Map<String, String> parameters) {
		StoredBlobSearch search = request.getSearch();
		search.applyParameters(parameters);
	}

}
