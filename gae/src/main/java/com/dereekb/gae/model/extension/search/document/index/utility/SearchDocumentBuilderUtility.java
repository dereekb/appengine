package com.dereekb.gae.model.extension.search.document.index.utility;

import java.util.Date;

import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.model.general.geo.utility.PointConverter;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.BooleanField;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.GeoPoint;

/**
 * Utility for building documents.
 *
 * @author dereekb
 */
public final class SearchDocumentBuilderUtility {

	public static void addBoolean(String format,
	                              String name,
	                              boolean value,
	                              Document.Builder builder) {
		addBoolean(String.format(format, name), value, builder);
	}

	public static void addBoolean(String name,
	                              boolean value,
	                              Document.Builder builder) {
		Field.Builder field = booleanField(name, value);
		builder.addField(field);
	}

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
		String atom = ((value) ? BooleanField.TRUE_ATOM : BooleanField.FALSE_ATOM);
		Field.Builder builder = Field.newBuilder().setName(name).setAtom(atom);
		return builder;
	}

	public static void addText(String format,
	                           String name,
	                           String value,
	                           Document.Builder builder) {
		addText(String.format(format, name), value, builder);
	}

	public static void addText(String name,
	                           String value,
	                           Document.Builder builder) {
		Field.Builder field = textField(name, value);
		builder.addField(field);
	}

	public static Field.Builder textField(String name,
	                                      String text) {
		return Field.newBuilder().setName(name).setText(text);
	}

	public static void addAtom(String format,
	                           String name,
	                           String value,
	                           Document.Builder builder) {
		addAtom(String.format(format, name), value, builder);
	}
	public static void addAtom(String name,
	                           String value,
	                           Document.Builder builder) {
		Field.Builder field = atomField(name, value);
		builder.addField(field);
	}

	public static void addAtom(String name,
	                           Number value,
	                           Document.Builder builder) {
		String stringValue = null;

		if (value != null) {
			stringValue = value.toString();
		}

		addAtom(name, stringValue, builder);
	}

	public static Field.Builder atomField(String name,
	                                      String value) {
		return Field.newBuilder().setName(name).setAtom(value);
	}

	public static void addDate(String format,
	                           String name,
	                           Date value,
	                           Document.Builder builder) throws IllegalArgumentException {
		addDate(String.format(format, name), value, builder);
	}

	public static void addDate(String name,
	                           Date value,
	                           Document.Builder builder) throws IllegalArgumentException {
		Field.Builder field = dateField(name, value);
		builder.addField(field);
	}

	public static Field.Builder dateField(String name,
	                                      Date date) throws IllegalArgumentException {
		if (date == null) {
			date = new Date(0);
		}

		return Field.newBuilder().setName(name).setDate(date);
	}

	public static void addGeoPoint(String format,
	                               String name,
	                               Point point,
	                               Document.Builder builder) {
		addGeoPoint(String.format(format, name), point, builder);
	}

	public static void addGeoPoint(String name,
	                               Point point,
	                               Document.Builder builder) {
		Field.Builder field = geoPointField(name, point);
		builder.addField(field);
	}

	public static Field.Builder geoPointField(String name,
	                                          Point point) {
		GeoPoint geoPoint = null;

		if (point != null) {
			geoPoint = PointConverter.convertToGeopoint(point);
		} else {
			geoPoint = new GeoPoint(0, 0);
		}

		return Field.newBuilder().setName(name).setGeoPoint(geoPoint);
	}

}
