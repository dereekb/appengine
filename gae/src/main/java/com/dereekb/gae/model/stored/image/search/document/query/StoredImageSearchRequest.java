package com.dereekb.gae.model.stored.image.search.document.query;

import com.dereekb.gae.model.extension.search.document.search.service.model.impl.AbstractModelDocumentRequest;
import com.dereekb.gae.model.geo.place.search.document.query.derivative.GeoPlaceSearchBuilder.GeoPlaceSearch;
import com.dereekb.gae.model.stored.blob.search.document.query.derivative.StoredBlobSearchBuilder.StoredBlobSearch;
import com.dereekb.gae.model.stored.image.StoredImage;

/**
 * Search request for a {@link StoredImage}.
 *
 * @author dereekb
 *
 */
public class StoredImageSearchRequest extends AbstractModelDocumentRequest {

	private GeoPlaceSearch geoPlaceSearch;
	private StoredBlobSearch storedBlobSearch;

	private String name;
	private String summary;
	private String tags;
	private String type;

	public GeoPlaceSearch getGeoPlaceSearch() {
		return this.geoPlaceSearch;
	}

	public void setGeoPlaceSearch(GeoPlaceSearch geoPlaceSearch) {
		this.geoPlaceSearch = geoPlaceSearch;
	}

	public StoredBlobSearch getStoredBlobSearch() {
		return this.storedBlobSearch;
	}

	public void setStoredBlobSearch(StoredBlobSearch storedBlobSearch) {
		this.storedBlobSearch = storedBlobSearch;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTags() {
		return this.tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
