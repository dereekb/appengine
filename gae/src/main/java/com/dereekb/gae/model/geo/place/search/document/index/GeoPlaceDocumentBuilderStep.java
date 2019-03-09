package com.dereekb.gae.model.geo.place.search.document.index;

import java.util.Date;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative.AbstractDerivableDocumentBuilderStep;
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
public class GeoPlaceDocumentBuilderStep extends AbstractDerivableDocumentBuilderStep<GeoPlace> {

	public static final String REGION_FIELD = "region";

	public static final String DERIVATIVE_PREFIX = "GP_";
	public static final String DERIVATIVE_FIELD_FORMAT = DERIVATIVE_PREFIX + "%s";

	public GeoPlaceDocumentBuilderStep() {
		super();
	}

	public GeoPlaceDocumentBuilderStep(String format, boolean inclusionStep) {
		super(format, inclusionStep);
	}

	// MARK: StagedDocumentBuilderStep
	@Override
	protected void performSharedStep(GeoPlace model,
	                                 Builder builder) {

		Date date = null;
		Point point = null;
		boolean isRegion = false;

		if (model != null) {
			point = model.getPoint();
			date = model.getDate();

			isRegion = model.isRegion();
		}

		// Creation Date
		ModelDocumentBuilderUtility.addDate(this.format, date, builder);

		// Point Field
		ModelDocumentBuilderUtility.addPoint(this.format, point, builder);

		// Is Region Field
		String isRegionFieldFormat = String.format(this.format, REGION_FIELD);
		SearchDocumentBuilderUtility.addBoolean(isRegionFieldFormat, isRegion, builder);

	}

	@Override
    public String toString() {
		return "GeoPlaceDocumentBuilderStep [format=" + this.format + ", inclusionStep=" + this.inclusionStep + "]";
    }

}
