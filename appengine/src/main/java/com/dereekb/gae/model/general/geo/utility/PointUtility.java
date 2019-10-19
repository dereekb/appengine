package com.dereekb.gae.model.general.geo.utility;

import org.apache.lucene.util.SloppyMath;

import com.dereekb.gae.model.general.geo.Point;
import com.google.appengine.api.search.GeoPoint;

/**
 * {@link Point} utility.
 *
 * @author dereekb
 *
 */
public class PointUtility {

	/**
	 * Checks whether or not the two points are within the specified number of
	 * meters.
	 *
	 * @param a
	 *            {@link Point}. Never {@code null}.
	 * @param b
	 *            {@link Point}. Never {@code null}.
	 * @param meters
	 *            Positive distance in meters.
	 * @return {@code true} if within the specified number of meters.
	 */
	public static boolean isWithinMeters(Point a,
	                                     Point b,
	                                     double meters) {
		return PointUtility.quickGetDistanceBetweenPoints(a, b) <= meters;
	}

	/**
	 * Returns an imprecise calculation of the distance between the two points.
	 *
	 * @param a
	 *            {@link Point}. Never {@code null}.
	 * @param b
	 *            {@link Point}. Never {@code null}.
	 * @return {@code double}.
	 */
	public static double quickGetDistanceBetweenPoints(Point a,
	                                                   Point b) {
		return SloppyMath.haversinMeters(a.getLatitude(), a.getLongitude(), b.getLatitude(), b.getLongitude());
	}

	/**
	 * Converts the input {@link Point} to a {@link GeoPoint}.
	 *
	 * @param point
	 *            {@link Point}. Never {@code null}.
	 * @return {@code GeoPoint}. Never {@code null}.
	 */
	public static GeoPoint convertToGeopoint(Point point) {
		Double latitude = point.getLatitude();
		Double longitude = point.getLongitude();

		GeoPoint geopoint = new GeoPoint(latitude, longitude);
		return geopoint;
	}

}
