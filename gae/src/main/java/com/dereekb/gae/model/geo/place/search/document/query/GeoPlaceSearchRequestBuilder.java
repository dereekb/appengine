package com.dereekb.gae.model.geo.place.search.document.query;

import java.util.Map;

import com.dereekb.gae.model.extension.search.document.search.model.DateSearch;
import com.dereekb.gae.model.extension.search.document.search.model.PointRadiusSearch;
import com.dereekb.gae.model.geo.place.search.document.index.GeoPlaceDocumentBuilderStep;
import com.dereekb.gae.model.geo.place.search.document.query.GeoPlaceSearchBuilder.GeoPlaceSearch;
import com.dereekb.gae.web.api.model.extension.search.impl.model.AbstractDescribedSearchRequestBuilder;


public class GeoPlaceSearchRequestBuilder extends AbstractDescribedSearchRequestBuilder<GeoPlaceSearchRequest> {

	protected GeoPlaceSearchRequestBuilder() {
		super(GeoPlaceSearchRequest.class);
	}

	@Override
	public void applyNonDescriptorParameters(GeoPlaceSearchRequest request,
	                            Map<String, String> parameters) {
		GeoPlaceSearch search = request.getSearch();

		if (parameters.containsKey(GeoPlaceDocumentBuilderStep.REGION_FIELD)) {
			String regionString = parameters.get(GeoPlaceDocumentBuilderStep.REGION_FIELD);
			Boolean region = new Boolean(parameters.get(regionString));
			search.setRegion(region);
		}

		search.setDate(DateSearch.fromString(parameters.get("date")));
		search.setPoint(PointRadiusSearch.fromString(parameters.get("point")));
	}

}
