package com.dereekb.gae.utilities.misc.parameters.impl;

import java.util.Map;

import com.dereekb.gae.utilities.collections.map.impl.CaseInsensitiveEntryContainer;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.parameters.MutableParameters;
import com.dereekb.gae.utilities.misc.parameters.Parameters;
import com.dereekb.gae.utilities.misc.parameters.utility.ParameterUtility;

/**
 * {@link Parameters} implementation that wraps a map.
 *
 * @author dereekb
 *
 */
public class ParametersImpl extends CaseInsensitiveEntryContainer<String>
        implements MutableParameters {

	public ParametersImpl() {}

	public ParametersImpl(Map<String, String> parameters) {
		super(parameters);
	}

	// MARK: MutableParameters
	@Override
	public final Map<String, String> getParameters() {
		return this.getEntries();
	}

	@Override
	public final void setParameters(Map<String, String> parameters) {
		super.setEntries(parameters);
	}

	// MARK: Utility
	public void addParameter(KeyedEncodedParameter parameter) {
		ParameterUtility.put(this.getEntries(), parameter);
	}

	public void addObjectParameter(String key,
	                               Object value) {
		this.addParameter(key, value.toString());
	}

	public void addParameter(String key,
	                         String value) {
		this.addEntry(key, value);
	}

	@Override
	public String toString() {
		return "ParametersImpl [parameters=" + this.getParameters() + "]";
	}

}
