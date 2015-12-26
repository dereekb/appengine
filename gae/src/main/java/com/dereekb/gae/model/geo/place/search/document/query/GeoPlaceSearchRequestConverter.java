package com.dereekb.gae.model.geo.place.search.document.query;

import com.dereekb.gae.model.extension.search.document.search.service.model.impl.AbstractModelDocumentRequestConverter;
import com.dereekb.gae.server.search.document.query.expression.Expression;


public class GeoPlaceSearchRequestConverter extends AbstractModelDocumentRequestConverter<GeoPlaceSearchRequest> {

	public GeoPlaceSearchRequestConverter() {
		this("GeoPlace");
	}

	public GeoPlaceSearchRequestConverter(String index) throws IllegalArgumentException {
		super(index);
	}

	@Override
	public Expression buildExpression(GeoPlaceSearchRequest request) {

		return null;
	}

}
