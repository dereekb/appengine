package com.dereekb.gae.utilities.misc.parameters.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.utilities.misc.keyed.utility.KeyedUtility;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.google.common.base.Joiner;

/**
 * {@link KeyedEncodedParameter} implementation.
 * 
 * @author dereekb
 *
 */
public class KeyedEncodedParameterImpl
        implements KeyedEncodedParameter {

	private String parameterKey;
	private String parameterString;

	public KeyedEncodedParameterImpl(String parameterKey, Object parameterObject) throws IllegalArgumentException {
		if (parameterObject == null) {
			throw new IllegalArgumentException("Parameter Object cannot be null.");
		}

		this.setParameterKey(parameterKey);
		this.setParameterString(parameterObject.toString());
	}

	public KeyedEncodedParameterImpl(String parameterKey, String parameterString) throws IllegalArgumentException {
		this.setParameterKey(parameterKey);
		this.setParameterString(parameterString);
	}

	@Override
	public String getParameterKey() {
		return this.parameterKey;
	}

	@Override
	public String getKeyValue() {
		return this.getParameterKey();
	}

	public void setParameterKey(String parameterKey) throws IllegalArgumentException {
		if (parameterKey == null || parameterKey.isEmpty()) {
			throw new IllegalArgumentException("Parameter cannot be empty or null.");
		}

		this.parameterKey = parameterKey;
	}

	@Override
	public String getParameterString() {
		return this.parameterString;
	}

	public void setParameterString(String parameterString) throws IllegalArgumentException {
		if (parameterString == null) {
			throw new IllegalArgumentException("ParameterString cannot be null.");
		}

		this.parameterString = parameterString;
	}

	@Override
	public String toString() {
		return "KeyedEncodedParameterImpl [parameterKey=" + this.parameterKey + ", parameterString="
		        + this.parameterString + "]";
	}

	/**
	 * Creates a single {@link KeyedEncodedParameterImpl} with the values joined
	 * together.
	 *
	 * @param param
	 *            Parameter name
	 * @param values
	 *            Values that will be joined by their toString() function value.
	 * @return {@link KeyedEncodedParameterImpl} instance. Never {@code null}.
	 * @throws IllegalArgumentException
	 *             if creating the {@link KeyedEncodedParameterImpl} fails.
	 */
	public static KeyedEncodedParameterImpl make(String param,
	                                             Iterable<? extends Object> values)
	        throws IllegalArgumentException {

		Joiner joiner = Joiner.on(",").skipNulls();
		String stringValues = joiner.join(values);
		KeyedEncodedParameterImpl pair = new KeyedEncodedParameterImpl(param, stringValues);
		return pair;
	}

	/**
	 * Creates a list of new {@link KeyedEncodedParameterImpl} instances, one
	 * for each
	 * input value.
	 *
	 * @param param
	 *            Parameter name
	 * @param values
	 *            List of values for each {@link KeyedEncodedParameterImpl}
	 * @return {@link List} of created {@link KeyedEncodedParameterImpl}.
	 * @throws IllegalArgumentException
	 *             if creating any
	 */
	public static List<KeyedEncodedParameterImpl> makeForValues(String param,
	                                                            Iterable<? extends Object> values)
	        throws IllegalArgumentException {

		List<KeyedEncodedParameterImpl> pairs = new ArrayList<KeyedEncodedParameterImpl>();

		for (Object value : values) {
			String valueString = value.toString();
			KeyedEncodedParameterImpl pair = new KeyedEncodedParameterImpl(param, valueString);
			pairs.add(pair);
		}

		return pairs;
	}

	/**
	 * Generates a list of {@link KeyedEncodedParameterImpl} using the input
	 * {@limk Map}
	 * of {@link Object} values. The map's keys are used as parameter names.
	 *
	 * @param map
	 *            Input map. Never {@code null}.
	 * @throws IllegalArgumentException
	 *             If
	 *             {@link KeyedEncodedParameterImpl#KeyedEncodedParameterImpl(String, Object)}
	 *             fails.
	 * @return {@link List} of {@link KeyedEncodedParameterImpl} values. Never
	 *         {@code null}.
	 */
	public static List<KeyedEncodedParameterImpl> makeParametersWithMap(Map<String, ? extends Object> map)
	        throws IllegalArgumentException {
		List<KeyedEncodedParameterImpl> pairs = new ArrayList<KeyedEncodedParameterImpl>();

		for (String parameter : map.keySet()) {
			Object value = map.get(parameter);
			KeyedEncodedParameterImpl pair = new KeyedEncodedParameterImpl(parameter, value);
			pairs.add(pair);
		}

		return pairs;
	}

	/**
	 * Removes all parameters with the same key as the replacement, then adds a
	 * single instance of the replacement.
	 * 
	 * @param inputParameters
	 *            {@link Collection} of parameters. Can be {@code null}.
	 * @param replacement
	 *            {@link KeyedEncodedParameter}. Never {@code null}.
	 * @return {@link List} with the values replaced.
	 */
	public static List<KeyedEncodedParameter> replaceInCollection(Collection<? extends KeyedEncodedParameter> inputParameters,
	                                                              KeyedEncodedParameter replacement) {
		return KeyedUtility.replaceInCollection(inputParameters, replacement);
	}

}
