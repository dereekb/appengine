package com.dereekb.gae.model.geo.place.search.document.index;

import java.util.Date;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative.DerivativeDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.util.ModelDocumentBuilderUtility;
import com.dereekb.gae.model.extension.search.document.index.utility.SearchDocumentBuilderUtility;
import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.geo.place.GeoPlaceInfoType;
import com.google.appengine.api.search.Document.Builder;

/**
 * {@link DerivativeDocumentBuilderStep} implementation for
 * {@link GeoPlaceInfoType} instances for adding {@link GeoPlace} types.
 *
 * @author dereekb
 *
 */
public class GeoPlaceDerivativeDocumentBuilderStep
        implements StagedDocumentBuilderStep<GeoPlace> {

	public static final String DEFAULT_PREFIX = "GP_";
	public final static String DEFAULT_FIELD_FORMAT = DEFAULT_PREFIX + "%s";

	private String format;

	public GeoPlaceDerivativeDocumentBuilderStep() {
		this(DEFAULT_FIELD_FORMAT);
	}

	public GeoPlaceDerivativeDocumentBuilderStep(String format) {
		this.format = format;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	public void performStep(GeoPlace model,
	                        Builder builder) {

		String id = null;
		Date date = null;
		boolean isRegion = false;
		Point point = null;

		if (model != null) {
			Long placeIdentifier = model.getIdentifier();
			id = placeIdentifier.toString();

			point = model.getPoint();
			isRegion = model.isRegion();
			date = model.getDate();
		}

		// Is Region Field
		String isRegionFieldFormat = String.format(this.format, "isRegion");
		SearchDocumentBuilderUtility.addBoolean(isRegionFieldFormat, isRegion, builder);

		// Creation Date
		ModelDocumentBuilderUtility.addDate(this.format, date, builder);

		// Identifier
		ModelDocumentBuilderUtility.addId(this.format, id, builder);

		// Point Field
		ModelDocumentBuilderUtility.addPoint(this.format, point, builder);

	}

	@Override
	public String toString() {
		return "GeoPlaceDerivativeDocumentBuilderStep [format=" + this.format + "]";
	}

}
