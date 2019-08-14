package com.dereekb.gae.model.extension.search.document.utility;

import com.dereekb.gae.server.search.query.expression.builder.SearchExpressionBuilder;
import com.dereekb.gae.server.search.query.expression.builder.impl.field.GeoDistanceField;
import com.dereekb.gae.utilities.query.builder.parameters.impl.LocationQueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.LocationQueryFieldParameter.LocationQueryData;

/**
 * Utility for building expressions with query field parameter values.
 *
 * @author dereekb
 *
 */
public class ModelSearchQueryFieldParameterUtility {

	public static SearchExpressionBuilder toExpression(LocationQueryFieldParameter location) {

		if (location == null) {
			return null;
		}

		String field = location.getField();
		LocationQueryData data = location.getValue();

		SearchExpressionBuilder expression = null;

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
