package com.dereekb.gae.model.stored.image.search.document.query;

import java.util.Map;

import com.dereekb.gae.model.geo.place.search.document.query.derivative.GeoPlaceSearchBuilder;
import com.dereekb.gae.model.stored.blob.search.document.query.derivative.StoredBlobSearchBuilder;
import com.dereekb.gae.web.api.model.extension.search.impl.model.AbstractSearchRequestBuilder;


public class StoredImageSearchRequestBuilder extends AbstractSearchRequestBuilder<StoredImageSearchRequest> {

	private GeoPlaceSearchBuilder geoPlaceSearchBuilder;
	private StoredBlobSearchBuilder storedBlobSearchBuilder;

	public StoredImageSearchRequestBuilder() {
		this(new GeoPlaceSearchBuilder(), new StoredBlobSearchBuilder());
	}

	public StoredImageSearchRequestBuilder(GeoPlaceSearchBuilder geoPlaceSearchBuilder,
	        StoredBlobSearchBuilder storedBlobSearchBuilder) {
		super(StoredImageSearchRequest.class);
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

	@Override
	public void applyParameters(StoredImageSearchRequest request,
	                            Map<String, String> parameters) {

		request.setName(parameters.get("name"));
		request.setSummary(parameters.get("summary"));
		request.setType(parameters.get("type"));
		request.setTags(parameters.get("tags"));

		request.setGeoPlaceSearch(this.geoPlaceSearchBuilder.make(parameters));
		request.setStoredBlobSearch(this.storedBlobSearchBuilder.make(parameters));
	}

}
