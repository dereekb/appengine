package com.dereekb.gae.utilities.misc.parameters.impl;

import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.utilities.misc.parameters.Parameters;

/**
 * {@link Parameters} implementation that wraps a map.
 * 
 * @author dereekb
 *
 */
public class ParametersImpl
        implements Parameters {

	private Map<String, String> parameters;

	public ParametersImpl() {
		this(new HashMap<String, String>());
	}

	public ParametersImpl(Map<String, String> parameters) {
		this.setParameters(parameters);
	}

	@Override
	public Map<String, String> getParameters() {
		return this.parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		if (parameters == null) {
			parameters = new HashMap<String, String>();
		}

		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "ParametersImpl [parameters=" + this.parameters + "]";
	}

}
