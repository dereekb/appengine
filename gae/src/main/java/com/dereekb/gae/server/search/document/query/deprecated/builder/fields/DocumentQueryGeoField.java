package com.dereekb.gae.server.search.document.query.deprecated.builder.fields;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.google.appengine.api.search.Field.FieldType;
import com.google.appengine.api.search.GeoPoint;

public class DocumentQueryGeoField extends DocumentQueryField {

	private static final String GEO_FIELD_FORMAT = "%s%s%s";
	private static final Integer DEFAULT_RADIUS = 1000;	//Default radius in meters.

	private static final ExpressionOperator DEFAULT_OPERATOR = ExpressionOperator.LessThan;
	
	private static class Distance {

		private static final String DEGREES_DOUBLE_FORMAT = "0.000000";
		private static final String GEO_POINT_FORMAT = "geopoint(%s, %s)";
		
		private static final String GEO_FIELD_DISTANCE_FORMAT = "distance(%s, %s)";
		
		private final String field;
		private final GeoPoint point;
		
		public Distance(String field, GeoPoint point) {
			this.field = field;
			this.point = point;
		}

		public String getGeoPointString() {
		    NumberFormat formatter = new DecimalFormat(DEGREES_DOUBLE_FORMAT);
			String latitudeString = formatter.format(this.point.getLatitude());
			String longitudeString = formatter.format(this.point.getLongitude());
			
			String geoPoint = String.format(GEO_POINT_FORMAT, latitudeString, longitudeString);
			return geoPoint;
		}
		
		public String toString() {
			String geoPoint = this.getGeoPointString();
			String distance = String.format(GEO_FIELD_DISTANCE_FORMAT, field, geoPoint);
			return distance;
		}
	}
	
	private final String field;
	private final GeoPoint point;
	private final Integer meterRadius;
	private final ExpressionOperator operator;

	public DocumentQueryGeoField(String field, GeoPoint point) {
		this(field, DEFAULT_OPERATOR, point, false);
	}

	public DocumentQueryGeoField(String field, GeoPoint point, Integer radius) {
		this(field, DEFAULT_OPERATOR, point, radius, false);
	}

	public DocumentQueryGeoField(String field, ExpressionOperator operator,
			GeoPoint point, Integer radius) {
		this(field, operator, point, radius, false);
	}

	public DocumentQueryGeoField(String field, ExpressionOperator operator,
			GeoPoint point, boolean not) {
		this(field, operator, point, DEFAULT_RADIUS, not);
	}

	public DocumentQueryGeoField(String field, ExpressionOperator operator,
			GeoPoint point, Integer radius, boolean not) {
		super(FieldType.GEO_POINT, not);
		this.field = field;
		this.point = point;
		this.operator = operator;
		this.meterRadius = radius;
	}

	@Override
	protected DocumentQueryField cloneQuery() {
		DocumentQueryGeoField clone = new DocumentQueryGeoField(this.field, this.operator,
				this.point, this.meterRadius, this.isNot());
		
		return clone;
	}

	@Override
	protected String getQueryString() {
		Distance distance = new Distance(field, this.point);
		String operatorString = this.operator.toString();
		String radius = this.meterRadius.toString();
		
	    String queryString = String.format(GEO_FIELD_FORMAT, distance, operatorString, radius);
		return queryString;
	}
}

