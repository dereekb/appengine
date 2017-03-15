package com.dereekb.gae.model.geo.place.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;

/**
 * {@link DirectionalConverter} for converting a {@link GeoPlace} to
 * {@link GeoPlaceData}.
 *
 * @author dereekb
 */
public final class GeoPlaceDataBuilder extends AbstractDirectionalConverter<GeoPlace, GeoPlaceData> {

	public GeoPlaceDataBuilder() {}

	// Single Directional Converter
	@Override
	public GeoPlaceData convertSingle(GeoPlace geoPlace) throws ConversionFailureException {
		GeoPlaceData data = new GeoPlaceData();

		// Id
		data.setModelKey(geoPlace.getModelKey());
		data.setDateValue(geoPlace.getDate());
		data.setSearchId(geoPlace.getSearchIdentifier());

		// Links
		data.setParent(ObjectifyKeyUtility.idFromKey(geoPlace.getParent()));
		data.setDescriptor(geoPlace.getDescriptor());

		// Data
		data.setPoint(geoPlace.getPoint());
		data.setRegion(geoPlace.getRegion());

		return data;
	}

}
