package com.dereekb.gae.model.stored.blob.search.document.query;

import java.util.Map;

import com.dereekb.gae.model.extension.search.document.search.model.DateSearch;
import com.dereekb.gae.model.extension.search.document.search.model.DescriptorSearch;
import com.dereekb.gae.web.api.model.extension.search.impl.model.AbstractSearchRequestBuilder;


public class StoredBlobSearchRequestBuilder extends AbstractSearchRequestBuilder<StoredBlobSearchRequest> {

	public StoredBlobSearchRequestBuilder() {
		super(StoredBlobSearchRequest.class);
	}

	@Override
	public void applyParameters(StoredBlobSearchRequest request,
	                            Map<String, String> parameters) {
		request.setName(parameters.get("name"));
		request.setEnding(parameters.get("ending"));
		request.setType(parameters.get("type"));

		request.setDate(DateSearch.fromString(parameters.get("date")));
		request.setDescriptor(DescriptorSearch.fromString(parameters.get("descriptor")));
	}

}
