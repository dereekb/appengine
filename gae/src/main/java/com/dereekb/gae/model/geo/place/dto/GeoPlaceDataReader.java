package com.dereekb.gae.model.geo.place.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.model.general.geo.Region;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringLongModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.dereekb.gae.utilities.misc.reader.DateLongConverter;

/**
 * {@link DirectionalConverter} for converting a {@link GeoPlace} to
 * {@link GeoPlaceData}.
 *
 * @author dereekb
 */
public final class GeoPlaceDataReader extends AbstractDirectionalConverter<GeoPlaceData, GeoPlace> {

	private static final ObjectifyKeyUtility<GeoPlace> GEO_PLACE_KEY_UTIL = ObjectifyKeyUtility.make(GeoPlace.class);
	private static final StringModelKeyConverter KEY_CONVERTER = StringLongModelKeyConverterImpl.CONVERTER;

	@Override
	public GeoPlace convertSingle(GeoPlaceData input) throws ConversionFailureException {
		GeoPlace geoPlace = new GeoPlace();

		// Identifier
		String stringIdentifier = input.getKey();
		geoPlace.setModelKey(KEY_CONVERTER.safeConvert(stringIdentifier));

		// Date
		Long date = input.getCreated();
		geoPlace.setDate(DateLongConverter.CONVERTER.safeConvert(date));

		// Links
		geoPlace.setParent(GEO_PLACE_KEY_UTIL.keyFromId(input.getParent()));
		geoPlace.setDescriptor(input.getDescriptor());

		// Data
		Point point = input.getPoint();
		geoPlace.setPoint(point);

		Region region = input.getRegion();
		geoPlace.setRegion(region);

		return geoPlace;
	}

}
