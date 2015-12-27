package com.dereekb.gae.model.geo.place.search.document.query;

import java.util.Map;

import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.search.document.search.model.DateSearch;
import com.dereekb.gae.model.extension.search.document.search.model.DescriptorSearch;
import com.dereekb.gae.model.extension.search.document.search.model.PointRadiusSearch;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.RawExpression;
import com.dereekb.gae.web.api.model.extension.search.ApiSearchReadRequest;


public class GeoPlaceSearchRequestBuilder
        implements SingleDirectionalConverter<ApiSearchReadRequest, GeoPlaceSearchRequest> {

	@Override
	public GeoPlaceSearchRequest convertSingle(ApiSearchReadRequest input) throws ConversionFailureException {

		GeoPlaceSearchRequest request = new GeoPlaceSearchRequest();

		Map<String, String> parameters = input.getParameters();
		String query = input.getQuery();

		if (query != null) {
			request.setOverride(new RawExpression(query));
		} else {
			request.setCursor(input.getCursor());

			// TODO: Add Region Boolean

			request.setDate(DateSearch.fromString(parameters.get("date")));
			request.setPoint(PointRadiusSearch.fromString(parameters.get("point")));
			request.setDescriptor(DescriptorSearch.fromString(parameters.get("descriptor")));
		}

		request.setLimit(input.getLimit());

		return request;
	}

}
