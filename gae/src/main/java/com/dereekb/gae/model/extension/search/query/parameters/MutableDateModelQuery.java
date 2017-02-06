package com.dereekb.gae.model.extension.search.query.parameters;

import java.util.Date;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.query.builder.parameters.impl.DateQueryFieldParameter;
import com.dereekb.gae.utilities.query.order.QueryResultsOrdering;

/**
 * Query against a date value.
 * 
 * @author dereekb
 * 
 * @see AbstractDateModelQuery
 */
public interface MutableDateModelQuery {

	public void searchDatesBefore(Date value);

	public void searchDatesAfter(Date value);

	public void searchDates(Date value,
	                        ExpressionOperator operator);

	public void orderByDatesDescending();

	public void orderByDates(QueryResultsOrdering ordering);

	public void setDate(String dateParameter);

	public void setDate(DateQueryFieldParameter date);

}
