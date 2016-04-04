package com.dereekb.gae.model.geo.place.search.document.query;

import com.dereekb.gae.model.extension.search.document.search.service.model.impl.AbstractModelDocumentRequestConverter;
import com.dereekb.gae.model.geo.place.search.document.query.derivative.GeoPlaceSearchBuilder.GeoPlaceSearch;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;

/**
 * Converts a {@link GeoPlaceSearchRequest} into a {@link ExpressionBuilder}.
 *
 * @author dereekb
 *
 */
public class GeoPlaceSearchRequestConverter extends AbstractModelDocumentRequestConverter<GeoPlaceSearchRequest> {

	public GeoPlaceSearchRequestConverter(String index) throws IllegalArgumentException {
		super(index);
	}

	@Override
	public ExpressionBuilder buildExpression(GeoPlaceSearchRequest request) {
		GeoPlaceSearch search = request.getSearch();
		ExpressionBuilder builder = search.makeExpression();

		if (request.getDescriptor() != null) {
			builder = builder.and(request.getDescriptor().make());
		}

		return builder;
	}

}
