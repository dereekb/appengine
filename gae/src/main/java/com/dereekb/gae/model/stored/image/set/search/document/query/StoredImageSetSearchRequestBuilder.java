package com.dereekb.gae.model.stored.image.set.search.document.query;

import java.util.Map;

import com.dereekb.gae.web.api.model.extension.search.impl.model.AbstractSearchRequestBuilder;

public class StoredImageSetSearchRequestBuilder extends AbstractSearchRequestBuilder<StoredImageSetSearchRequest> {

	public StoredImageSetSearchRequestBuilder() {
		super(StoredImageSetSearchRequest.class);
	}

	@Override
    public void applyParameters(StoredImageSetSearchRequest request,
	                            Map<String, String> parameters) {

		request.setLabel(parameters.get("label"));
		request.setDetail(parameters.get("detail"));
		request.setTags(parameters.get("tags"));
	}

}
