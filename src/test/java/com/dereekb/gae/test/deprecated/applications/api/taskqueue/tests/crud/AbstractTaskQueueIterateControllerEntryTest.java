package com.dereekb.gae.test.applications.api.taskqueue.tests.crud;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.test.deprecated.applications.api.taskqueue.tests.extension.iterate.AbstractIterateTaskExecutorTest;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateController;

/**
 * Used for testing entries within the {@link TaskQueueIterateController}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractTaskQueueIterateControllerEntryTest<T extends ObjectifyModel<T>> extends AbstractIterateTaskExecutorTest<T> {

	protected String modelType;

	@Autowired
	@Qualifier("taskQueueIterateController")
	protected TaskQueueIterateController controller;

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	/**
	 * Used for testing an iteration.
	 *
	 * @author dereekb
	 *
	 */
	protected class TaskQueueIterateTest {

		private Integer count = 10;

		private String taskName;
		private Map<String, String> parameters;

		public TaskQueueIterateTest(String taskName) {
			this(taskName, null);
		}

		public TaskQueueIterateTest(String taskName, Map<String, String> parameters) {
			this.setTaskName(taskName);
			this.setParameters(parameters);
		}

		public void setTaskName(String taskName) {
			if (taskName == null || taskName.isEmpty()) {
				throw new IllegalArgumentException("Taskname unspecified.");
			}

			this.taskName = taskName;
		}

		public void setParameters(Map<String, String> parameters) {
			this.parameters = parameters;
		}

		protected List<T> generate() {
			return AbstractTaskQueueIterateControllerEntryTest.this.modelGenerator.generate(this.count);
		}

		public void test() {
			List<T> models = this.generate();
			AbstractTaskQueueIterateControllerEntryTest.this.controller.iterate(
			        AbstractTaskQueueIterateControllerEntryTest.this.modelType, this.taskName, 0, null, this.parameters);

			models = this.reloadModels(models);
			this.checkResults(models);
		}

		protected List<T> reloadModels(List<T> input) {
			return input;
		}

		protected void checkResults(List<T> models) {

		}

	}

}
