package com.dereekb.gae.server.search.query.builder.fields.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.utilities.query.ExpressionOperator;
import com.google.appengine.api.search.Field.FieldType;

/**
 * Geo search field.
 *
 * @author dereekb
 *
 */
@Deprecated
public class SearchQueryGeoField extends SearchQueryFieldImpl {

	private static final String GEO_FIELD_FORMAT = "%s%s%s";
	private static final Integer DEFAULT_RADIUS = 1000;	// Default radius in meters.

	private static final ExpressionOperator DEFAULT_OPERATOR = ExpressionOperator.LESS_THAN;

	private static class Distance {

		private static final String DEGREES_DOUBLE_FORMAT = "0.000000";
		private static final String GEO_POINT_FORMAT = "geopoint(%s, %s)";

		private static final String GEO_FIELD_DISTANCE_FORMAT = "distance(%s, %s)";

		private final String field;
		private final Point point;

		public Distance(String field, Point point) {
			this.field = field;
			this.point = point;
		}

		public String getPointString() {
			NumberFormat formatter = new DecimalFormat(DEGREES_DOUBLE_FORMAT);
			String latitudeString = formatter.format(this.point.getLatitude());
			String longitudeString = formatter.format(this.point.getLongitude());

			String geoPoint = String.format(GEO_POINT_FORMAT, latitudeString, longitudeString);
			return geoPoint;
		}

		@Override
		public String toString() {
			String geoPoint = this.getPointString();
			String distance = String.format(GEO_FIELD_DISTANCE_FORMAT, this.field, geoPoint);
			return distance;
		}
	}

	private final String field;
	private final Point point;
	private final Integer meterRadius;
	private final ExpressionOperator operator;

	public SearchQueryGeoField(String field, Point point) {
		this(field, DEFAULT_OPERATOR, point, false);
	}

	public SearchQueryGeoField(String field, Point point, Integer radius) {
		this(field, DEFAULT_OPERATOR, point, radius, false);
	}

	public SearchQueryGeoField(String field, ExpressionOperator operator, Point point, Integer radius) {
		this(field, operator, point, radius, false);
	}

	public SearchQueryGeoField(String field, ExpressionOperator operator, Point point, boolean not) {
		this(field, operator, point, DEFAULT_RADIUS, not);
	}

	public SearchQueryGeoField(String field, ExpressionOperator operator, Point point, Integer radius, boolean not) {
		super(FieldType.GEO_POINT, not);
		this.field = field;
		this.point = point;
		this.operator = operator;
		this.meterRadius = radius;
	}

	@Override
	protected SearchQueryFieldImpl cloneQuery() {
		SearchQueryGeoField clone = new SearchQueryGeoField(this.field, this.operator, this.point, this.meterRadius,
		        this.isNot());

		return clone;
	}

	@Override
	protected String getQueryString() {
		Distance distance = new Distance(this.field, this.point);
		String operatorString = this.operator.toString();
		String radius = this.meterRadius.toString();

		String queryString = String.format(GEO_FIELD_FORMAT, distance, operatorString, radius);
		return queryString;
	}
}
