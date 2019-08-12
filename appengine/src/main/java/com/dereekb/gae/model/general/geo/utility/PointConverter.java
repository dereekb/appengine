package com.dereekb.gae.model.general.geo.utility;

import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.model.general.geo.impl.PointImpl;
import com.google.appengine.api.search.GeoPoint;

/**
 * Converts {@link PointImpl} into other objects.
 *
 * @author dereekb
 *
 */
public class PointConverter {

	public static GeoPoint convertToGeopoint(Point point) {
		Double latitude = point.getLatitude();
		Double longitude = point.getLongitude();

		GeoPoint geopoint = new GeoPoint(latitude, longitude);
		return geopoint;
	}

}
