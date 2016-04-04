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
public class GeoPlaceDocumentBuilderStep
        implements StagedDocumentBuilderStep<GeoPlace> {

	public static final String REGION_FIELD = "region";

	public static final String DERIVATIVE_PREFIX = "GP_";
	public static final String DERIVATIVE_FIELD_FORMAT = DERIVATIVE_PREFIX + "%s";

	private String format;
	private boolean derivative;

	public GeoPlaceDocumentBuilderStep() {
		this("%s", false);
	}

	public GeoPlaceDocumentBuilderStep(String format, boolean derivative) {
		this.setFormat(format);
		this.setDerivative(derivative);
	}

	public static GeoPlaceDocumentBuilderStep derivativeBuilder() {
		return new GeoPlaceDocumentBuilderStep();
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public boolean isDerivative() {
		return this.derivative;
	}

	public void setDerivative(boolean derivative) {
		this.derivative = derivative;
	}

	// MARK: StagedDocumentBuilderStep
	@Override
	public void performStep(GeoPlace model,
	                        Builder builder) {

		boolean isRegion = false;
		String id = null;

		Date date = null;
		Point point = null;

		if (model != null) {
			Long placeIdentifier = model.getIdentifier();
			id = placeIdentifier.toString();

			point = model.getPoint();
			isRegion = model.isRegion();
			date = model.getDate();
		}

		// Is Region Field
		String isRegionFieldFormat = String.format(this.format, REGION_FIELD);
		SearchDocumentBuilderUtility.addBoolean(isRegionFieldFormat, isRegion, builder);

		// Creation Date
		ModelDocumentBuilderUtility.addDate(this.format, date, builder);

		// Point Field
		ModelDocumentBuilderUtility.addPoint(this.format, point, builder);

		if (this.derivative) {

			// Identifier
			ModelDocumentBuilderUtility.addId(this.format, id, builder);
		} else {

			// Descriptors
			ModelDocumentBuilderUtility.addDescriptorInfo(this.format, model, builder);
		}

	}

	@Override
	public String toString() {
		return "GeoPlaceDocumentBuilderStep [format=" + this.format + ", derivative=" + this.derivative + "]";
	}

}
