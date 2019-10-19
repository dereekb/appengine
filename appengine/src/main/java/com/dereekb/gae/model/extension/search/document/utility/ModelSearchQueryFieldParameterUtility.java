package com.dereekb.gae.model.extension.search.document.utility;

import java.util.Date;

import com.dereekb.gae.server.search.query.expression.builder.impl.field.AtomField;
import com.dereekb.gae.server.search.query.expression.builder.impl.field.DateField;
import com.dereekb.gae.server.search.query.expression.builder.impl.field.GeoDistanceField;
import com.dereekb.gae.server.search.query.expression.builder.impl.field.TimeNumberField;
import com.dereekb.gae.utilities.query.ExpressionOperator;
import com.dereekb.gae.utilities.query.builder.parameters.impl.AbstractQueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.DateQueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.LocationQueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.LocationQueryFieldParameter.LocationQueryData;

/**
 * Utility for building expressions with query field parameter values.
 *
 * @author dereekb
 *
 */
public class ModelSearchQueryFieldParameterUtility {

	public static AtomField toAtomExpression(AbstractQueryFieldParameter<?> parameter) {

		if (parameter == null) {
			return null;
		}

		String field = parameter.getField();
		Object value = parameter.getValue();

		AtomField expression = new AtomField(field, value);
		return expression;
	}

	public static DateField toExpression(DateQueryFieldParameter parameter) {

		if (parameter == null) {
			return null;
		}

		String field = parameter.getField();
		Date value = parameter.getValue();
		ExpressionOperator operator = parameter.getOperator();

		DateField expression = new DateField(field, value, operator);
		return expression;
	}

	public static TimeNumberField toTimeNumberExpression(DateQueryFieldParameter parameter) {

		if (parameter == null) {
			return null;
		}

		String field = parameter.getField();
		Date value = parameter.getValue();
		ExpressionOperator operator = parameter.getOperator();

		TimeNumberField expression = new TimeNumberField(field, value, operator);
		return expression;
	}

	public static GeoDistanceField toExpression(LocationQueryFieldParameter location) {

		if (location == null) {
			return null;
		}

		String field = location.getField();
		LocationQueryData data = location.getValue();

		GeoDistanceField expression = null;

		switch (data.getType()) {
			case RADIUS:
				expression = new GeoDistanceField(field, data.getPoint(), data.getRadius(), location.getOperator());
				break;
			default:
				throw new UnsupportedOperationException("This type of query is not supported.");
		}

		return expression;
	}

}
