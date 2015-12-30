package com.dereekb.gae.model.stored.image.search.document.query;

import java.util.Map;

import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.geo.place.search.document.query.derivative.GeoPlaceSearchBuilder;
import com.dereekb.gae.model.stored.blob.search.document.query.derivative.StoredBlobSearchBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.RawExpression;
import com.dereekb.gae.web.api.model.extension.search.ApiSearchReadRequest;


public class StoredImageSearchRequestBuilder
        implements SingleDirectionalConverter<ApiSearchReadRequest, StoredImageSearchRequest> {

	private GeoPlaceSearchBuilder geoPlaceSearchBuilder;
	private StoredBlobSearchBuilder storedBlobSearchBuilder;

	public StoredImageSearchRequestBuilder() {
		this(new GeoPlaceSearchBuilder(), new StoredBlobSearchBuilder());
	}

	public StoredImageSearchRequestBuilder(GeoPlaceSearchBuilder geoPlaceSearchBuilder,
	        StoredBlobSearchBuilder storedBlobSearchBuilder) {
		this.geoPlaceSearchBuilder = geoPlaceSearchBuilder;
		this.storedBlobSearchBuilder = storedBlobSearchBuilder;
	}

	public GeoPlaceSearchBuilder getGeoPlaceSearchBuilder() {
		return this.geoPlaceSearchBuilder;
	}

	public void setGeoPlaceSearchBuilder(GeoPlaceSearchBuilder geoPlaceSearchBuilder) {
		this.geoPlaceSearchBuilder = geoPlaceSearchBuilder;
	}

	public StoredBlobSearchBuilder getStoredBlobSearchBuilder() {
		return this.storedBlobSearchBuilder;
	}

	public void setStoredBlobSearchBuilder(StoredBlobSearchBuilder storedBlobSearchBuilder) {
		this.storedBlobSearchBuilder = storedBlobSearchBuilder;
	}

	// MARK: Single Directional Converter
	@Override
	public StoredImageSearchRequest convertSingle(ApiSearchReadRequest input) throws ConversionFailureException {

		StoredImageSearchRequest request = new StoredImageSearchRequest();

		Map<String, String> parameters = input.getParameters();
		String query = input.getQuery();

		if (query != null) {
			request.setOverride(new RawExpression(query));
		} else {
			request.setName(parameters.get("name"));
			request.setSummary(parameters.get("summary"));
			request.setType(parameters.get("type"));
			request.setTags(parameters.get("tags"));

			request.setGeoPlaceSearch(this.geoPlaceSearchBuilder.make(parameters));
			request.setStoredBlobSearch(this.storedBlobSearchBuilder.make(parameters));
		}

		request.setLimit(input.getLimit());
		request.setCursor(input.getCursor());

		return request;
	}

}
