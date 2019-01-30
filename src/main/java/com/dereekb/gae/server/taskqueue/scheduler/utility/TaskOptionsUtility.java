package com.dereekb.gae.server.taskqueue.scheduler.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.google.appengine.api.taskqueue.TaskOptions;

public class TaskOptionsUtility {

	public static TaskOptions appendHeader(TaskOptions options,
	                                       KeyedEncodedParameter parameter) {
		return appendHeaders(options, new SingleItem<KeyedEncodedParameter>(parameter));
	}

	public static TaskOptions appendHeaders(TaskOptions options,
	                                        Collection<KeyedEncodedParameter> parameters) {
		for (KeyedEncodedParameter pair : parameters) {
			options = options.header(pair.getParameterKey(), pair.getParameterString());
		}

		return options;
	}

	public static TaskOptions appendParameter(TaskOptions options,
	                                          KeyedEncodedParameter parameter) {
		return appendParameters(options, new SingleItem<KeyedEncodedParameter>(parameter));
	}

	public static TaskOptions appendParameters(TaskOptions options,
	                                           Collection<KeyedEncodedParameter> parameters) {
		for (KeyedEncodedParameter pair : parameters) {
			options = options.param(pair.getParameterKey(), pair.getParameterString());
		}

		return options;
	}

	public static List<TaskOptions> appendHeaders(List<TaskOptions> options,
	                                             Iterable<KeyedEncodedParameter> headers) {

		for (KeyedEncodedParameter header : headers) {
			options = appendHeader(options, header);
		}

		return options;
	}

	public static List<TaskOptions> appendHeader(List<TaskOptions> options,
	                                             KeyedEncodedParameter header) {
		List<TaskOptions> newOptions = new ArrayList<TaskOptions>(options.size());

		for (TaskOptions option : options) {
			TaskOptions newOption = appendHeader(option, header);
			newOptions.add(newOption);
		}

		return newOptions;
	}

}
