package com.dereekb.gae.model.extension.search.document.index.utility;

import java.util.Date;

import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.model.general.geo.utility.PointConverter;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.GeoPoint;

/**
 * Utility for building documents.
 *
 * @author dereekb
 */
public final class SearchDocumentBuilderUtility {

	public static String TRUE_ATOM = "true";
	public static String FALSE_ATOM = "false";
	public static String NONE_VALUE = "NONE";

	/**
	 * Creates a boolean Atom.
	 *
	 * @param name
	 *            Field Name
	 * @param value
	 *            Field Value
	 * @return Atom {@link Field.Builder} for the input values.
	 */
	public static Field.Builder booleanField(String name,
	                                         boolean value) {
		String atom = ((value) ? TRUE_ATOM : FALSE_ATOM);
		Field.Builder builder = Field.newBuilder().setName(name).setAtom(atom);
		return builder;
	}

	public static Field.Builder textField(String name,
	                                      String text) {
		return Field.newBuilder().setName(name).setText(text);
	}

	public static Field.Builder atomField(String name,
	                                      String value) {
		return Field.newBuilder().setName(name).setAtom(value);
	}

	public static Field.Builder dateField(String name,
	                                      Date date) {
		return Field.newBuilder().setName(name).setDate(date);
	}

	public static Field.Builder geoPointField(String name,
	                                          Point point) {
		Field.Builder field = null;

		if (point != null) {
			GeoPoint geoPoint = PointConverter.convertToGeopoint(point);
			field = Field.newBuilder().setName(name).setGeoPoint(geoPoint);
		}

		return field;
	}

}
