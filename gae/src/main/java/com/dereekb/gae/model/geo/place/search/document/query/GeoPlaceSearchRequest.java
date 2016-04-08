package com.dereekb.gae.model.geo.place.search.document.query;

import com.dereekb.gae.model.extension.search.document.search.service.model.impl.AbstractDescribedModelDocumentRequest;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.geo.place.search.document.query.GeoPlaceSearchBuilder.GeoPlaceSearch;

/**
 * Search request for a {@link GeoPlace}.
 *
 * @author dereekb
 *
 */
public class GeoPlaceSearchRequest extends AbstractDescribedModelDocumentRequest {

	public static final GeoPlaceSearchBuilder BUILDER = new GeoPlaceSearchBuilder();

	private GeoPlaceSearch search;

	public GeoPlaceSearchRequest() {
		this.search = BUILDER.make();
	}

	public GeoPlaceSearch getSearch() {
		return this.search;
	}

	public void setSearch(GeoPlaceSearch search) throws IllegalArgumentException {
		if (search == null) {
			throw new IllegalArgumentException("Search cannot be null.");
		}

		this.search = search;
	}

	@Override
	public String toString() {
		return "GeoPlaceSearchRequest [search=" + this.search + ", descriptor=" + this.descriptor + "]";
	}

}
