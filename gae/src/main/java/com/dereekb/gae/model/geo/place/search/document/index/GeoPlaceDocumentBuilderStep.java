package com.dereekb.gae.model.geo.place.search.document.index;

import java.util.Date;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.util.ModelDocumentBuilderUtility;
import com.dereekb.gae.model.extension.search.document.index.utility.SearchDocumentBuilderUtility;
import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.google.appengine.api.search.Document.Builder;

/**
 * Implementation of {@link StagedDocumentBuilderStep} for adding
 * {@link GeoPlaceDocumentBuilderStep}.
 *
 * @author dereekb
 */
public final class GeoPlaceDocumentBuilderStep
        implements StagedDocumentBuilderStep<GeoPlace> {

	public static final String REGION_FIELD = "region";

	@Override
	public void performStep(GeoPlace model,
	                        Builder builder) {

		// Point Field
		// Is Region Field
		boolean isRegion = model.isRegion();
		SearchDocumentBuilderUtility.addBoolean(REGION_FIELD, isRegion, builder);

		// Date
		Date date = model.getDate();
		ModelDocumentBuilderUtility.addDate(date, builder);

		// Point
		Point point = model.getPoint();
		ModelDocumentBuilderUtility.addPoint(point, builder);

		// Descriptors
		ModelDocumentBuilderUtility.addDescriptorInfo(model, builder);

	}

}
