package com.dereekb.gae.server.taskqueue.deprecated;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.taskqueue.system.KeyedEncodedParameter;
import com.dereekb.gae.utilities.collections.pairs.HandlerPair;
import com.google.common.base.Joiner;

/**
 * Pair of parameters. The parameters and values cannot be changed.
 *
 * @author dereekb
 * @deprecated Replaced by {@link KeyedEncodedParameter}.
 */
@Deprecated
public class TaskQueueParamPair extends HandlerPair<String, String> {

	public TaskQueueParamPair(String param, String value) throws IllegalArgumentException {
		super(param, value);

		if (param == null || param.isEmpty()) {
			throw new IllegalArgumentException("Parameter must be specified.");
		}
	}

	public String getParameter() {
		return this.key;
	}

	public String getValue() {
		return this.object;
	}

	/**
	 * Creates a single {@link TaskQueueParameterPair} with the values joined
	 * together.
	 *
	 * @param param
	 *            Parameter name
	 * @param values
	 *            Values that will be joined by their toString() function value.
	 * @return {@link TaskQueueParamPair} instance.
	 * @throws IllegalArgumentException
	 *             if param is null or empty.
	 */
	public static TaskQueueParamPair pairWithCommaSeparatedValue(String param,
	                                                                 Iterable<? extends Object> values)
	        throws IllegalArgumentException {

		Joiner joiner = Joiner.on(",").skipNulls();
		String stringValues = joiner.join(values);
		TaskQueueParamPair pair = new TaskQueueParamPair(param, stringValues);
		return pair;
	}

	/**
	 * Creates a list of new {@link TaskQueueParamPair} instances, one for each
	 * input value.
	 *
	 * @param param
	 *            Parameter name
	 * @param values
	 *            List of values for each {@link TaskQueueParamPair}
	 * @return {@link List} of created {@link TaskQueueParamPair}.
	 * @throws IllegalArgumentException
	 *             if param is null or empty.
	 */
	public static List<TaskQueueParamPair> pairsForValues(String param,
	                                                         Iterable<? extends Object> values)
	        throws IllegalArgumentException {

		List<TaskQueueParamPair> pairs = new ArrayList<TaskQueueParamPair>();

		for (Object value : values) {
			String valueString = value.toString();
			TaskQueueParamPair pair = new TaskQueueParamPair(param, valueString);
			pairs.add(pair);
		}

		return pairs;
	}
}
