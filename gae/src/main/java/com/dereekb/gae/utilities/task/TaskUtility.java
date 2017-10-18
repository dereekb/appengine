package com.dereekb.gae.utilities.task;

import java.util.List;

public class TaskUtility {

	public static <T> void runTask(List<T> input,
	                               Task<T> task) {
		for (T item : input) {
			task.doTask(item);
		}
	}

}
