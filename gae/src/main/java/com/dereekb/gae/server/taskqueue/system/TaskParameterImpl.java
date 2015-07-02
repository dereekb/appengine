package com.dereekb.gae.server.taskqueue.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;

/**
 * {@link TaskParameter} implementation.
 *
 * @author dereekb
 *
 */
public class TaskParameterImpl
        implements TaskParameter {

	public final String parameter;

	public final String value;

	public TaskParameterImpl(String parameter, String value) {
		this.parameter = parameter;
		this.value = value;
	}

	@Override
	public String getParameter() {
		return this.parameter;
	}

	@Override
	public String getValue() {
		return this.value;
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
	 *             if param is null or empty.
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

	public static List<TaskParameterImpl> makeParametersWithMap(Map<String, ? extends Object> map) {

		List<TaskParameterImpl> pairs = new ArrayList<TaskParameterImpl>();

		for (String parameter : map.keySet()) {
			Object value = map.get(parameter);
			String stringValue = value.toString();

			TaskParameterImpl pair = new TaskParameterImpl(parameter, stringValue);
			pairs.add(pair);
		}

		return pairs;
	}

	public static List<TaskParameter> replaceParameterInCollection(Collection<TaskParameter> inputParameters,
	                                                               TaskParameter replacement) {
		List<TaskParameter> newParameters = new ArrayList<TaskParameter>();

		if (inputParameters != null) {
			for (TaskParameter parameter : inputParameters) {
				String param = parameter.getParameter();

				// Filter out parameters with the same name.
				if (param.equals(replacement) == false) {
					newParameters.add(parameter);
				}
			}
		}

		newParameters.add(replacement);
		return newParameters;
	}

}
