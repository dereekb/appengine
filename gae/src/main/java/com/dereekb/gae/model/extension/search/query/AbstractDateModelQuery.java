package com.dereekb.gae.model.extension.search.query;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableEncodedQueryParameters;
import com.dereekb.gae.utilities.query.builder.parameters.impl.DateQueryFieldParameter;

/**
 * Used by classes that search with a date field.
 * 
 * @author dereekb
 *
 */
public class AbstractDateModelQuery
        implements ConfigurableEncodedQueryParameters {

	public static final String DEFAULT_DATE_FIELD = "date";

	private DateQueryFieldParameter date;

	public DateQueryFieldParameter getDate() {
		return this.date;
	}

	public void searchDatesBefore(Date value) {
		this.date = new DateQueryFieldParameter(this.getDateField(), value);
	}

	public void searchDatesAfter(Date value) {
		this.date = new DateQueryFieldParameter(this.getDateField(), ExpressionOperator.GREATER_THAN, value);
	}

	public void orderByDatesDescending() {
		this.date = DateQueryFieldParameter.sortByDatesDescending();
	}

	protected String getDateField() {
		return DEFAULT_DATE_FIELD;
	}

	// MARK: ConfigurableQueryParameters
	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = new HashMap<String, String>();

		if (this.date != null) {
			parameters.put(this.getDateField(), this.date.getParameterString());
		}

		return parameters;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		this.setDateParameters(parameters);
	}

	protected void setDateParameters(Map<String, String> parameters) {
		String dateString = parameters.get(this.getDateField());

		if (dateString != null) {
			this.date = new DateQueryFieldParameter(this.getDateField(), dateString);
		} else {
			this.date = null;
		}
	}

}
