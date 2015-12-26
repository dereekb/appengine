package com.dereekb.gae.model.geo.place.search.document.query;

import java.util.Map;

import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.search.document.search.model.DateSearch;
import com.dereekb.gae.web.api.model.extension.search.ApiSearchReadRequest;


public class GeoPlaceSearchRequestBuilder
        implements SingleDirectionalConverter<ApiSearchReadRequest, GeoPlaceSearchRequest> {

	@Override
	public GeoPlaceSearchRequest convertSingle(ApiSearchReadRequest input) throws ConversionFailureException {

		Integer limit = input.getLimit();
		Map<String, String> parameters = input.getParameters();

		GeoPlaceSearchRequest request = new GeoPlaceSearchRequest();

		DateSearch date = DateSearch.fromString(parameters.get("date"));

		return request;
	}

}
