package com.dereekb.gae.server.search.query.expression.builder.impl.field;

import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.server.search.query.expression.builder.SearchExpressionBuilder;
import com.dereekb.gae.utilities.query.ExpressionOperator;

/**
 * Geo distance query that searches for all points within a radius.
 *
 * @author dereekb
 *
 */
public class GeoDistanceField extends AbstractGeoField {

	/**
	 * Distance format.
	 *
	 * distance(fieldName, geopoint(...)) > Radius
	 */
	private static final String FORMAT = "distance(%s, %s) %s %s";

	private Integer radius;
	private ExpressionOperator operator;

	public GeoDistanceField(String name, Point point, Integer radius) {
		this(name, point, radius, ExpressionOperator.LESS_THAN);
	}

	public GeoDistanceField(String name, Point point, Integer radius, ExpressionOperator operator) {
		super(name, point);
		this.setRadius(radius);
		this.setOperator(operator);
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
		if (operator == null) {
			operator = ExpressionOperator.LESS_THAN;
		}

		this.operator = operator;
	}

	@Override
	public String getQueryExpression() {
		String geoPointString = this.getGeoPointString();
		String comparisonString = this.operator.toString();
		String distanceString = String.format(FORMAT, this.name, geoPointString, comparisonString, this.radius);
		return distanceString;
	}

	@Override
	public boolean isComplex() {
		return true;
	}

	@Override
	public SearchExpressionBuilder copyExpression() {
		return new GeoDistanceField(this.name, this.point, this.radius, this.operator);
	}

	@Override
	public String toString() {
		return "GeoDistanceField [radius=" + this.radius + ", operator=" + this.operator + "]";
	}

}
