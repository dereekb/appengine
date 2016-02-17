package com.dereekb.gae.server.search.document.query.expression.builder.impl.field;

import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;


public class GeoDistanceField extends GeoField {

	/**
	 * Distance format.
	 *
	 * distance(fieldName, geopoint(...)) > Radius
	 */
	private static final String FORMAT = "distance(%s, %s) %s %s";

	private Integer radius;
	private ExpressionOperator operator;

    public GeoDistanceField(String name, Point point, Integer radius) {
	    this(name, point, radius, ExpressionOperator.LessThan);
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
			operator = ExpressionOperator.LessThan;
		}

    	this.operator = operator;
    }

	@Override
	public String getExpressionValue() {
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
	public ExpressionBuilder copyExpression() {
		return new GeoDistanceField(this.name, this.point, this.radius, this.operator);
	}

}
