package com.dereekb.gae.model.geo.place.dto;

import java.util.Date;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.model.general.geo.Region;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringLongModelKeyConverter;
import com.googlecode.objectify.Key;

/**
 * {@link DirectionalConverter} for converting a {@link GeoPlace} to
 * {@link GeoPlaceData}.
 *
 * @author dereekb
 */
public final class GeoPlaceDataReader extends AbstractDirectionalConverter<GeoPlaceData, GeoPlace> {

	private static final StringLongModelKeyConverter keyConverter = new StringLongModelKeyConverter();

	@Override
	public GeoPlace convertSingle(GeoPlaceData input) throws ConversionFailureException {
		GeoPlace geoPlace = new GeoPlace();

		// Identifier
		String stringIdentifier = input.getIdentifier();
		if (stringIdentifier != null) {
			ModelKey key = keyConverter.convertSingle(stringIdentifier);
			Long identifier = key.getId();
			geoPlace.setIdentifier(identifier);
		}

		// Date
		Long created = input.getCreated();

		if (created != null) {
			Date date = new Date(created);
			geoPlace.setDate(date);
		}

		// Links
		Long parent = input.getParent();

		if (parent != null) {
			Key<GeoPlace> parentKey = Key.create(GeoPlace.class, parent);
			geoPlace.setParent(parentKey);
		}

		String infoType = input.getInfoType();
		String infoIdentifier = input.getInfoIdentifier();

		geoPlace.setInfoType(infoType);
		geoPlace.setInfoIdentifier(infoIdentifier);

		// Data
		Point point = input.getPoint();
		geoPlace.setPoint(point);

		Region region = input.getRegion();
		geoPlace.setRegion(region);

		return geoPlace;
	}

}
