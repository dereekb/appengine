package com.dereekb.gae.model.extension.search.document.search.model;

import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.GeoDistanceField;

/**
 * Search model for a {@link Point} that searches around a radius.
 *
 * @author dereekb
 *
 */
public class PointRadiusSearch {

	private static final String SPLITTER = ",";

	private Point point;
	private Integer radius;
	private ExpressionOperator operator;

	public PointRadiusSearch(Point point, Integer radius) {
		this(point, radius, ExpressionOperator.LESS_THAN);
	}

	public PointRadiusSearch(Point point, Integer radius, ExpressionOperator operator) {
		this.setPoint(point);
		this.setRadius(radius);
		this.setOperator(operator);
	}

	public Point getPoint() {
		return this.point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Integer getRadius() {
		return this.radius;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
	}

	public ExpressionOperator getOperator() {
		return this.operator;
	}

	public void setOperator(ExpressionOperator operator) {
		this.operator = operator;
	}

	/**
	 * Creates a {@link PointRadiusSearch} from the input string.
	 * <p>
	 * Format: LAT(Double),LONG(Double),Radius(Integer),OP?(String)
	 *
	 * @param pointString
	 * @return {@link PointRadiusSearch} or {@code null} if nothing is input.
	 * @throws IllegalArgumentException
	 */
	public static PointRadiusSearch fromString(String pointString) throws IllegalArgumentException {
		PointRadiusSearch point = null;

		if (pointString != null && pointString.isEmpty() == false) {
			try {
				String[] split = pointString.split(SPLITTER);

				Double latitude, longitude;
				Integer radius = null;
				ExpressionOperator operator = null;

				switch (split.length) {
					default:
					case 4:
						operator = ExpressionOperator.fromString(split[3]);
					case 3:
						latitude = new Double(split[0]);
						longitude = new Double(split[1]);
						radius = new Integer(split[2]);
						break;
					case 2:
					case 1:
						throw new IllegalArgumentException();
				}

				point = new PointRadiusSearch(new Point(latitude, longitude), radius, operator);
			} catch (Exception e) {
				throw new IllegalArgumentException("Could not create point radius.", e);
			}
		}

		return point;
	}

	public ExpressionBuilder make(String field) {
		ExpressionBuilder builder = new GeoDistanceField(field, this.point, this.radius, this.operator);
		return builder;
	}

	@Override
	public String toString() {
		return "PointRadiusSearch [point=" + this.point + ", radius=" + this.radius + ", operator=" + this.operator
		        + "]";
	}

}
