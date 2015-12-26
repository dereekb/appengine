package com.dereekb.gae.model.extension.search.document.search.model;

import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.GeoDistanceField;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.NotExpression;

/**
 * Search model for a {@link Point} that searches around a radius.
 * 
 * @author dereekb
 *
 */
public class PointRadiusSearch {

	private Boolean not = false;

	private Point point;
	private Integer radius;
	private ExpressionOperator operator = ExpressionOperator.LessThan;

	public PointRadiusSearch() {}

	public Boolean getNot() {
		return this.not;
	}

	public void setNot(Boolean not) {
		this.not = not;
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

	public ExpressionBuilder make(String field) {
		ExpressionBuilder builder = new GeoDistanceField(field, this.point, this.radius, this.operator);

		if (this.not) {
			builder = new NotExpression(builder);
		}

		return builder;
	}

	@Override
	public String toString() {
		return "PointRadiusSearch [not=" + this.not + ", point=" + this.point + ", radius="
		        + this.radius + ", operator=" + this.operator + "]";
	}

}
