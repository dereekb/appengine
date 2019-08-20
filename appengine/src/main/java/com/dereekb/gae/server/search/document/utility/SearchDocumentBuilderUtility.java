package com.dereekb.gae.server.search.document.utility;

import java.util.Date;

import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.model.general.geo.utility.PointConverter;
import com.dereekb.gae.server.search.query.expression.builder.impl.field.BooleanField;
import com.dereekb.gae.server.search.query.expression.builder.impl.field.TimeNumberField;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.misc.keyed.IndexCoded;
import com.dereekb.gae.utilities.time.DateUtility;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.GeoPoint;
import com.googlecode.objectify.Key;

/**
 * Utility for building documents.
 *
 * @author dereekb
 */
public final class SearchDocumentBuilderUtility {

	// MARK: Boolean
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

	// MARK: Text
	public static void addText(String format,
	                           String name,
	                           String value,
	                           Document.Builder builder) {
		addText(String.format(format, name), value, builder);
	}

	public static void addText(String name,
	                           Iterable<String> values,
	                           Document.Builder builder) {
		String value = StringUtility.joinValues(values, " ");
		addText(name, value, builder);
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

	// MARK: Atom
	public static void addAtom(String name,
	                           Key<?> key,
	                           Document.Builder builder) {
		String value = null;

		if (key != null) {
			value = key.toString();
		}

		addAtom(name, value, builder);
	}

	public static void addAtom(String name,
	                           IndexCoded codedValue,
	                           Document.Builder builder) {
		addAtom(name, codedValue.getCode(), builder);
	}

	public static void addAtom(String format,
	                           String name,
	                           Key<?> key,
	                           Document.Builder builder) {
		String value = null;

		if (key != null) {
			value = key.toString();
		}

		addAtom(format, name, value, builder);
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

	public static void addAtom(String format,
	                           String name,
	                           Number value,
	                           Document.Builder builder) {
		addAtom(String.format(format, name), value, builder);
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

	// MARK: Date
	public static void addDate(String format,
	                           String name,
	                           Date value,
	                           Document.Builder builder)
	        throws IllegalArgumentException {
		addDate(String.format(format, name), value, builder);
	}

	public static void addDate(String name,
	                           Date value,
	                           Document.Builder builder)
	        throws IllegalArgumentException {
		Field.Builder field = dateField(name, value);
		builder.addField(field);
	}

	public static Field.Builder dateField(String name,
	                                      Date date)
	        throws IllegalArgumentException {
		if (date == null) {
			date = new Date(0);
		}

		return Field.newBuilder().setName(name).setDate(date);
	}

	// MARK: Time
	public static void addTimeNumber(String name,
	                                 Date date,
	                                 Builder builder) {
		Double time = null;

		if (date != null) {
			time = DateUtility.dateToRoundedTimeDouble(date, TimeNumberField.TIME_FIELD_ROUNDING);
		}

		addNumber(name, time, builder);
	}

	// MARK: Number
	public static void addNumber(String name,
	                             Number number,
	                             Builder builder) {
		Field.Builder field = numberField(name, number);
		builder.addField(field);
	}

	public static Field.Builder numberField(String name,
	                                        Number number) {

		Double value = null;

		if (number != null) {
			value = number.doubleValue();

			if (value.intValue() > Integer.MAX_VALUE || value.intValue() < Integer.MIN_VALUE) {
				throw new IllegalArgumentException("Number must be between the min and max Integer values.");
			}
		}

		return Field.newBuilder().setName(name).setNumber(value);
	}

	// MARK: Geopoint
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
