package com.dereekb.gae.model.geo.place.search.document.query;

import com.dereekb.gae.model.extension.search.document.search.model.DescriptorSearch;
import com.dereekb.gae.model.extension.search.document.search.service.model.impl.AbstractModelDocumentRequest;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.geo.place.search.document.query.derivative.GeoPlaceSearchBuilder;
import com.dereekb.gae.model.geo.place.search.document.query.derivative.GeoPlaceSearchBuilder.GeoPlaceSearch;

/**
 * Search request for a {@link GeoPlace}.
 *
 * @author dereekb
 *
 */
public class GeoPlaceSearchRequest extends AbstractModelDocumentRequest {

	public static final GeoPlaceSearchBuilder BUILDER = new GeoPlaceSearchBuilder();

	private GeoPlaceSearch search;
	private DescriptorSearch descriptor;

	public GeoPlaceSearchRequest() {
		this.search = BUILDER.make();
	}

	public GeoPlaceSearch getSearch() {
		return this.search;
	}

	public void setSearch(GeoPlaceSearch search) {
		this.search = search;
	}

	public DescriptorSearch getDescriptor() {
		return this.descriptor;
	}

	public void setDescriptor(DescriptorSearch descriptor) {
		this.descriptor = descriptor;
	}

	@Override
	public String toString() {
		return "GeoPlaceSearchRequest [search=" + this.search + ", descriptor=" + this.descriptor + "]";
	}

}
