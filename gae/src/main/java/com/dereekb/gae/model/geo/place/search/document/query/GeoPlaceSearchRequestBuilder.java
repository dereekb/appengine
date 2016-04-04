package com.dereekb.gae.model.geo.place.search.document.query;

import java.util.Map;

import com.dereekb.gae.model.extension.search.document.search.model.DateSearch;
import com.dereekb.gae.model.extension.search.document.search.model.DescriptorSearch;
import com.dereekb.gae.model.extension.search.document.search.model.PointRadiusSearch;
import com.dereekb.gae.model.geo.place.search.document.index.GeoPlaceDocumentBuilderStep;
import com.dereekb.gae.model.geo.place.search.document.query.derivative.GeoPlaceSearchBuilder.GeoPlaceSearch;
import com.dereekb.gae.web.api.model.extension.search.impl.model.AbstractSearchRequestBuilder;


public class GeoPlaceSearchRequestBuilder extends AbstractSearchRequestBuilder<GeoPlaceSearchRequest> {

	protected GeoPlaceSearchRequestBuilder() {
		super(GeoPlaceSearchRequest.class);
	}

	@Override
	public void applyParameters(GeoPlaceSearchRequest request,
	                            Map<String, String> parameters) {
		GeoPlaceSearch search = request.getSearch();

		if (parameters.containsKey(GeoPlaceDocumentBuilderStep.REGION_FIELD)) {
			String regionString = parameters.get(GeoPlaceDocumentBuilderStep.REGION_FIELD);
			Boolean region = new Boolean(parameters.get(regionString));
			search.setRegion(region);
		}

		search.setDate(DateSearch.fromString(parameters.get("date")));
		search.setPoint(PointRadiusSearch.fromString(parameters.get("point")));
		request.setDescriptor(DescriptorSearch.fromString(parameters.get("descriptor")));
	}

}
