package com.dereekb.gae.model.geo.place.search.document.query;

import com.dereekb.gae.model.deprecated.geo.place.search.document.query.GeoPlaceSearchBuilder.GeoPlaceSearch;
import com.dereekb.gae.model.extension.search.document.search.service.model.impl.AbstractDescribedModelDocumentRequestConverter;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;

/**
 * Converts a {@link GeoPlaceSearchRequest} into a {@link ExpressionBuilder}.
 *
 * @author dereekb
 *
 */
public class GeoPlaceSearchRequestConverter extends AbstractDescribedModelDocumentRequestConverter<GeoPlaceSearchRequest> {

	public GeoPlaceSearchRequestConverter(String index) throws IllegalArgumentException {
		super(index);
	}

	// MARK: AbstractDescribedModelDocumentRequest
	@Override
	public ExpressionBuilder buildExpression(GeoPlaceSearchRequest request) {
		ExpressionBuilder builder = super.buildExpression(request);

		GeoPlaceSearch search = request.getSearch();
		builder = builder.and(search.makeExpression());

		return builder;
	}

	@Override
	public String toString() {
		return "GeoPlaceSearchRequestConverter []";
	}

}
