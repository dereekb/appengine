package com.dereekb.gae.utilities.misc.parameters.filter;

import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.impl.AbstractFilter;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;

/**
 * Utility filter for filtering parameters by parameter and/or string.
 *
 * @author dereekb
 *
 */
public class KeyedEncodedParameterFilter extends AbstractFilter<KeyedEncodedParameter> {

	private String parameter;
	private String string;

	public KeyedEncodedParameterFilter() {}

	public KeyedEncodedParameterFilter(String parameter, String string) {
		this.setParameter(parameter);
		this.setString(string);
	}

	public String getParameter() {
		return this.parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getString() {
		return this.string;
	}

	public void setString(String string) {
		this.string = string;
	}

	// MARK: Filter
	@Override
	public FilterResult filterObject(KeyedEncodedParameter object) {
		boolean isOk = true;

		String parameter = object.getParameterKey();
		String string = object.getParameterString();

		if (this.string != null) {
			isOk = this.string.equals(string);
		}

		if (isOk && this.parameter != null) {
			isOk = this.parameter.equals(parameter);
		}

		return FilterResult.withBoolean(isOk);
	}

}
