package com.dereekb.gae.utilities.query.builder.parameters.impl;

import java.util.Set;

import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.model.general.geo.impl.PointImpl;
import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.dereekb.gae.utilities.query.ExpressionOperator;
import com.dereekb.gae.utilities.query.builder.parameters.impl.LocationQueryFieldParameter.LocationQueryData;
import com.dereekb.gae.utilities.query.order.QueryResultsOrdering;

/**
 * Query field for a {@link Point}-type query.
 *
 * @author dereekb
 *
 */
public class LocationQueryFieldParameter extends AbstractQueryFieldParameter<LocationQueryData> {

	private static final Set<ExpressionOperator> ALLOWED_OPERATORS = SetUtility.makeSet(ExpressionOperator.GREATER_THAN, ExpressionOperator.LESS_THAN);

	public LocationQueryFieldParameter() {
		super();
	}

	public LocationQueryFieldParameter(LocationQueryFieldParameter parameter) throws IllegalArgumentException {
		super(parameter);
	}

	public LocationQueryFieldParameter(String field, LocationQueryFieldParameter parameter)
	        throws IllegalArgumentException {
		super(field, parameter);
	}

	public LocationQueryFieldParameter(String field,
	        ExpressionOperator operator,
	        LocationQueryData value,
	        QueryResultsOrdering ordering) {
		super(field, operator, value, ordering);
	}

	public LocationQueryFieldParameter(String field, LocationQueryData value) {
		this(field, value, ExpressionOperator.LESS_THAN);
	}

	public LocationQueryFieldParameter(String field, LocationQueryData value, ExpressionOperator operator) {
		super(field, operator, value);
	}

	public LocationQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
		super(field, parameterString);
	}

	public LocationQueryFieldParameter(String field) {
		super(field);
	}

	public static LocationQueryFieldParameter make(String locationField,
	                                               LocationQueryData loc) {
		LocationQueryFieldParameter fieldParameter = null;

		if (loc != null) {
			fieldParameter = new LocationQueryFieldParameter(locationField, loc);
		}

		return fieldParameter;
	}

	public static LocationQueryFieldParameter make(String locationField,
	                                               LocationQueryFieldParameter loc) {
		LocationQueryFieldParameter fieldParameter = null;

		if (loc != null) {
			fieldParameter = new LocationQueryFieldParameter(locationField, loc);
		}

		return fieldParameter;
	}

	public static LocationQueryFieldParameter make(String locationField,
	                                               String loc) {
		LocationQueryFieldParameter fieldParameter = null;

		if (loc != null) {
			fieldParameter = new LocationQueryFieldParameter(locationField, loc);
		}

		return fieldParameter;
	}

	@Override
	public void setOperator(ExpressionOperator operator) throws IllegalArgumentException {
		if (ALLOWED_OPERATORS.contains(operator) == false) {
			throw new IllegalArgumentException("Disallowed operator. Try using EQUAL.");
		}

		super.setOperator(operator);
	}

	// MARK: LocationQueryData
	@Override
	protected String encodeParameterValue(LocationQueryData value) {
		switch (value.getType()) {
			case RADIUS:
				return RadiusLocationQueryDataDencoder.encodeParameterValue(value);
			default:
				throw new UnsupportedOperationException("Unknown tyoe");
		}
	}

	@Override
	protected LocationQueryData decodeParameterValue(String value) throws IllegalArgumentException {
		String[] split = value.split(",");
		String typeString = split[0];

		switch (typeString) {
			case "r":
			case "radius":
			default:
				return RadiusLocationQueryDataDencoder.decodeParameterValue(split);
		}
	}

	// MARK: Internal
	/**
	 * Location Query Type
	 *
	 * @author dereekb
	 *
	 */
	public static enum LocationQueryType {

		RADIUS

	}

	/**
	 * Data for a {@link LocationQueryFieldParameter}
	 *
	 * @author dereekb
	 *
	 */
	public static class LocationQueryData {

		private LocationQueryType type;
		private Point point;
		private Double radius;

		public LocationQueryData(LocationQueryType type, Point point, Double radius) {
			super();
			this.setType(type);
			this.setPoint(point);
			this.setRadius(radius);
		}

		public LocationQueryType getType() {
			return this.type;
		}

		public void setType(LocationQueryType type) {
			if (type == null) {
				throw new IllegalArgumentException("type cannot be null.");
			}

			this.type = type;
		}

		public Point getPoint() {
			return this.point;
		}

		public void setPoint(Point point) {
			if (point == null) {
				throw new IllegalArgumentException("point cannot be null.");
			}

			this.point = point;
		}

		public Double getRadius() {
			return this.radius;
		}

		public void setRadius(Double radius) {
			if (radius == null) {
				throw new IllegalArgumentException("radius cannot be null.");
			}

			this.radius = radius;
		}

	}

	private static class RadiusLocationQueryDataDencoder {

		private static final String FORMAT = "r,%.6f,%.6f,%.6f";

		public static String encodeParameterValue(LocationQueryData value) {
			return String.format(FORMAT, value.point.getLatitude(), value.point.getLongitude(), value.radius);
		}

		public static LocationQueryData decodeParameterValue(String[] split) throws IllegalArgumentException {

			String latitudeString = split[1];
			String longitudeString = split[2];
			String distanceString = split[3];

			Double latitude;

			try {
				latitude = new Double(latitudeString);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid latitude.");
			}

			Double longitude;

			try {
				longitude = new Double(longitudeString);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid longitude.");
			}

			Point point = PointImpl.makeValidPoint(latitude, longitude);

			Double distance;

			try {
				distance = new Double(distanceString);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid distance.");
			}

			return new LocationQueryData(LocationQueryType.RADIUS, point, distance);
		}

	}

}
