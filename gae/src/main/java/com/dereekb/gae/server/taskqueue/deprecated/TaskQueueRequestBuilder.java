package com.dereekb.gae.server.taskqueue.deprecated;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.utilities.collections.SingleItem;

/**
 * Helper class for building and modifying requests.
 *
 * @author dereekb
 */
@Deprecated
public class TaskQueueRequestBuilder {

	public static final String DEFAULT_SPLIT_REGEX = ",";

	public List<TaskQueuePushRequest> splitTaskAtParameter(String parameter,
	                                                       TaskQueuePushRequest input) {
		return this.splitTaskAtParameter(parameter, DEFAULT_SPLIT_REGEX, input);
	}

	/**
	 * Splits a comma-separate parameter in one task, and convert into multiple tasks, using the input task as a base to copy from.
	 *
	 * @param parameter
	 * @return
	 */
	public List<TaskQueuePushRequest> splitTaskAtParameter(String parameter,
	                                                       String regex,
	                                                       TaskQueuePushRequest input) {
		List<TaskQueuePushRequest> requests = new ArrayList<TaskQueuePushRequest>();
		TaskQueueParamPair splitPair = this.pairWithName(parameter, input.getParameters());

		if (splitPair != null) {
			List<TaskQueueParamPair> otherPairs = this.filterParameters(parameter, input.getParameters());
			String value = splitPair.getValue();
			String[] split = value.split(regex);

			for (String val : split) {
				TaskQueuePushRequest copy = input.copy(false);
				List<TaskQueueParamPair> pairs = new ArrayList<TaskQueueParamPair>(otherPairs);

				TaskQueueParamPair valuePair = new TaskQueueParamPair(parameter, val);
				pairs.add(valuePair);
				copy.setParameters(pairs);
				requests.add(copy);
			}
		} else {
			requests.add(input);
		}

		return requests;
	}

	public TaskQueueParamPair pairWithName(String parameter,
	                                       Iterable<TaskQueueParamPair> input) {
		TaskQueueParamPair result = null;

		for (TaskQueueParamPair pair : input) {
			if (pair.getParameter().equalsIgnoreCase(parameter)) {
				result = pair;
				break;
			}
		}

		return result;
	}

	public List<TaskQueueParamPair> filterParameters(String parameter,
	                                                 Iterable<TaskQueueParamPair> input) {
		return this.filterParameters(SingleItem.withValue(parameter), input);
	}

	public List<TaskQueueParamPair> filterParameters(Collection<String> parameters,
	                                                 Iterable<TaskQueueParamPair> input) {
		List<TaskQueueParamPair> pairs = new ArrayList<TaskQueueParamPair>();
		Set<String> disallowedSet = new HashSet<String>(parameters);

		for (TaskQueueParamPair pair : input) {
			String parameter = pair.getParameter();
			if (disallowedSet.contains(parameter) == false) {
				pairs.add(pair);
			}
		}

		return pairs;
	}

}
