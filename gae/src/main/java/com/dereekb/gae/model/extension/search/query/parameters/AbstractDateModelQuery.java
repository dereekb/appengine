package com.dereekb.gae.model.extension.search.query.parameters;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.misc.parameters.utility.ParameterUtility;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableEncodedQueryParameters;
import com.dereekb.gae.utilities.query.builder.parameters.impl.DateQueryFieldParameter;
import com.dereekb.gae.utilities.query.order.QueryResultsOrdering;

/**
 * Used by classes that search with a date field.
 * 
 * @author dereekb
 *
 */
public class AbstractDateModelQuery
        implements MutableDateModelQuery, ConfigurableEncodedQueryParameters {

	public static final String DEFAULT_DATE_FIELD = "date";

	private DateQueryFieldParameter date;

	public AbstractDateModelQuery() {}

	public AbstractDateModelQuery(Map<String, String> parameters) throws IllegalArgumentException {
		this.setParameters(parameters);
	}

	public DateQueryFieldParameter getDate() {
		return this.date;
	}

	@Override
	public void searchDatesBefore(Date value) {
		this.date = new DateQueryFieldParameter(this.getDateField(), value);
	}

	@Override
	public void searchDatesAfter(Date value) {
		this.date = new DateQueryFieldParameter(this.getDateField(), ExpressionOperator.GREATER_THAN, value);
	}

	@Override
	public void searchNewestFirst() {
		this.date = DateQueryFieldParameter.searchNewestFirst(this.getDateField());
	}

	@Override
	public void searchOldestFirst() {
		this.date = DateQueryFieldParameter.searchOldestFirst(this.getDateField());
	}

	@Override
	public void searchDates(Date value,
	                        ExpressionOperator operator) {
		this.date = new DateQueryFieldParameter(this.getDateField(), operator, value);
	}

	@Override
	public void orderByDatesDescending() {
		this.date = DateQueryFieldParameter.sortByDatesDescending(this.getDateField());
	}

	@Override
	public void orderByDates(QueryResultsOrdering ordering) {
		this.date = DateQueryFieldParameter.sortByDates(this.getDateField(), ordering);
	}

	@Override
	public void setDate(String date) {
		this.date = DateQueryFieldParameter.make(this.getDateField(), date);
	}

	@Override
	public void setDate(DateQueryFieldParameter date) {
		this.date = DateQueryFieldParameter.make(this.getDateField(), date);
	}

	protected String getDateField() {
		return DEFAULT_DATE_FIELD;
	}

	// MARK: ConfigurableQueryParameters
	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = new HashMap<String, String>();
		ParameterUtility.put(parameters, this.date);
		return parameters;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		this.setDateParameters(parameters);
	}

	protected void setDateParameters(Map<String, String> parameters) {
		this.setDate(parameters.get(this.getDateField()));
	}

}
