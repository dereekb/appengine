package com.dereekb.gae.model.geo.place.search.document.index;

import java.util.Date;

import com.dereekb.gae.model.extension.search.document.index.component.builder.impl.derivative.DerivativeDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.utility.SearchDocumentBuilderUtility;
import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.geo.place.GeoPlaceInfoType;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;

/**
 * {@link DerivativeDocumentBuilderStep} implementation for
 * {@link GeoPlaceInfoType} instances for adding {@link GeoPlace} types.
 *
 * @author dereekb
 *
 */
public final class GeoPlaceDerivativeDocumentBuilderStep
        implements DerivativeDocumentBuilderStep<GeoPlaceInfoType> {

	public final static String DEFAULT_FIELD_FORMAT = "GP_%s";

	private final String format;
	private final Getter<GeoPlace> placeGetter;

	public GeoPlaceDerivativeDocumentBuilderStep(Getter<GeoPlace> placeGetter) {
		this(placeGetter, DEFAULT_FIELD_FORMAT);
	}

	public GeoPlaceDerivativeDocumentBuilderStep(Getter<GeoPlace> placeGetter, String format) {
		this.placeGetter = placeGetter;
		this.format = format;
	}

	public String getFormat() {
		return this.format;
	}

	public Getter<GeoPlace> getPlaceGetter() {
		return this.placeGetter;
	}

	@Override
	public void updateBuilder(String identifier,
	                          Builder builder) {

		ModelKey modelKey = new ModelKey(identifier);
		GeoPlace place = this.placeGetter.get(modelKey);

		String placeIdString = null;
		Date date = null;
		boolean isRegion = false;
		Point point = null;

		if (place != null) {
			point = place.getPoint();
			isRegion = place.isRegion();

			Long placeIdentifier = place.getIdentifier();
			placeIdString = placeIdentifier.toString();
			date = place.getDate();
		}

		// Place Identifier
		String identifierFieldFormat = String.format(this.format, "id");
		Field.Builder identifierField = SearchDocumentBuilderUtility.atomField(identifierFieldFormat, placeIdString);
		builder.addField(identifierField);

		// Creation Date
		String dateFieldFormat = String.format(this.format, "date");
		Field.Builder dateField = SearchDocumentBuilderUtility.dateField(dateFieldFormat, date);
		builder.addField(dateField);

		// Point Field
		String pointFieldFormat = String.format(this.format, "point");
		Field.Builder pointField = SearchDocumentBuilderUtility.geoPointField(pointFieldFormat, point);
		builder.addField(pointField);

		// Is Region Field
		String isRegionFieldFormat = String.format(this.format, "isRegion");
		Field.Builder isRegionField = SearchDocumentBuilderUtility.booleanField(isRegionFieldFormat, isRegion);
		builder.addField(isRegionField);

	}

}
