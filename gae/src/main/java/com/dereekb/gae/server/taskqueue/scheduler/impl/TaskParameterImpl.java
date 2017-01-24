package com.dereekb.gae.server.taskqueue.scheduler.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.server.taskqueue.scheduler.TaskParameter;
import com.dereekb.gae.utilities.misc.parameters.impl.KeyedEncodedParameterImpl;
import com.google.common.base.Joiner;

/**
 * {@link TaskParameter} implementation.
 *
 * @author dereekb
 * @deprecated use {@link KeyedEncodedParameterImpl} instead.
 */
@Deprecated
public class TaskParameterImpl
        implements TaskParameter {

	public String parameter;

	public String value;

	public TaskParameterImpl(String parameter, String value) {
		this.setParameter(parameter);
		this.setValue(value);
	}

	/**
	 * Constructor for contructing a {@link TaskParameterImpl} from an
	 * {@link Object}'s {@link Object#toString()} value.
	 *
	 * @param parameter
	 * @param value
	 */
	public TaskParameterImpl(String parameter, Object value) {
		if (value == null) {
			throw new IllegalArgumentException("Object value cannot be null.");
		}

		this.setParameter(parameter);
		this.setValue(value.toString());
	}

	@Override
	public String getParameterKey() {
		return this.parameter;
	}

	public void setParameter(String parameter) {
		if (parameter == null || parameter.isEmpty()) {
			throw new IllegalArgumentException("Parameter cannot be null or empty.");
		}

		this.parameter = parameter;
	}

	@Override
	public String getParameterString() {
		return this.value;
	}

	public void setValue(String value) {
		if (value == null || value.isEmpty()) {
			throw new IllegalArgumentException("Value cannot be null or empty.");
		}

		this.value = value;
	}

	/**
	 * Creates a single {@link TaskQueueParameterPair} with the values joined
	 * together.
	 *
	 * @param param
	 *            Parameter name
	 * @param values
	 *            Values that will be joined by their toString() function value.
	 * @return {@link TaskParameterImpl} instance.
	 * @throws IllegalArgumentException
	 *             if param is null or empty.
	 */
	public static TaskParameterImpl parametersWithCommaSeparatedValue(String param,
	                                                                  Iterable<? extends Object> values)
	        throws IllegalArgumentException {

		Joiner joiner = Joiner.on(",").skipNulls();
		String stringValues = joiner.join(values);
		TaskParameterImpl pair = new TaskParameterImpl(param, stringValues);
		return pair;
	}

	/**
	 * Creates a list of new {@link TaskParameterImpl} instances, one for each
	 * input value.
	 *
	 * @param param
	 *            Parameter name
	 * @param values
	 *            List of values for each {@link TaskParameterImpl}
	 * @return {@link List} of created {@link TaskParameterImpl}.
	 * @throws IllegalArgumentException
	 *             if parameter is {@code null} or empty.
	 */
	public static List<TaskParameterImpl> makeParametersForValues(String param,
	                                                              Iterable<? extends Object> values)
	        throws IllegalArgumentException {

		List<TaskParameterImpl> pairs = new ArrayList<TaskParameterImpl>();

		for (Object value : values) {
			String valueString = value.toString();
			TaskParameterImpl pair = new TaskParameterImpl(param, valueString);
			pairs.add(pair);
		}

		return pairs;
	}

	/**
	 * Generates a list of {@link TaskParameterImpl} using the input {@limk Map}
	 * of {@link Object} values. The map's keys are used as parameter names.
	 *
	 * @param map
	 *            Input map. Never {@code null}.
	 * @throws IllegalArgumentException
	 *             If
	 *             {@link TaskParameterImpl#TaskParameterImpl(String, Object)}
	 *             fails.
	 * @return {@link List} of {@link TaskParameterImpl} values. Never
	 *         {@code null}.
	 */
	@Deprecated
	public static List<TaskParameterImpl> makeParametersWithMap(Map<String, ? extends Object> map)
	        throws IllegalArgumentException {
		List<TaskParameterImpl> pairs = new ArrayList<TaskParameterImpl>();

		for (String parameter : map.keySet()) {
			Object value = map.get(parameter);
			TaskParameterImpl pair = new TaskParameterImpl(parameter, value);
			pairs.add(pair);
		}

		return pairs;
	}

	@Deprecated
	public static List<TaskParameter> replaceParameterInCollection(Collection<TaskParameter> inputParameters,
	                                                               TaskParameter replacement) {
		List<TaskParameter> newParameters = new ArrayList<TaskParameter>();

		if (inputParameters != null) {
			for (TaskParameter parameter : inputParameters) {
				String param = parameter.getParameterKey();

				// Filter out parameters with the same name.
				if (param.equals(replacement) == false) {
					newParameters.add(parameter);
				}
			}
		}

		newParameters.add(replacement);
		return newParameters;
	}

	@Override
	public String toString() {
		return "TaskParameterImpl [parameter=" + this.parameter + ", value=" + this.value + "]";
	}

}
