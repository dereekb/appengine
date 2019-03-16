package com.dereekb.gae.model.geo.place.search.document.query;

import java.util.Map;

import com.dereekb.gae.model.deprecated.geo.place.search.document.query.GeoPlaceSearchBuilder.GeoPlaceSearch;
import com.dereekb.gae.web.api.model.extension.search.impl.model.AbstractDescribedSearchRequestBuilder;

public class GeoPlaceSearchRequestBuilder extends AbstractDescribedSearchRequestBuilder<GeoPlaceSearchRequest> {

	protected GeoPlaceSearchRequestBuilder() {
		super(GeoPlaceSearchRequest.class);
	}

	@Override
	public void applyNonDescriptorParameters(GeoPlaceSearchRequest request,
	                            Map<String, String> parameters) {
		GeoPlaceSearch search = request.getSearch();
		search.applyParameters(parameters);

	}

}
