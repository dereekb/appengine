package com.dereekb.gae.test.applications.api.taskqueue.tests.crud;

import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.crud.task.impl.delete.ScheduleDeleteTask;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.Setter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.scheduler.TaskParameter;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.taskqueue.controller.crud.TaskQueueEditController;
import com.dereekb.gae.web.taskqueue.controller.crud.TaskQueueEditControllerEntry;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * Abstract test for a {@link TaskQueueEditControllerEntry}.
 * <p>
 * Executes the review then checks with the implementation to see if all
 * expected changes occurred.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class TaskQueueEditControllerEntryTest<T extends UniqueModel> extends ApiApplicationTestContext {

	private Integer genCount = 2;

	private String modelTaskQueueType;

	private GetterSetter<T> getterSetter;
	private TestModelGenerator<T> modelGenerator;
	private ScheduleDeleteTask<T> deleteTask;

	@Autowired
	@Qualifier("taskQueueEditController")
	private TaskQueueEditController controller;

    public Integer getGenCount() {
		return this.genCount;
	}

	public void setGenCount(Integer genCount) {
		this.genCount = genCount;
	}

	public String getModelTaskQueueType() {
		return this.modelTaskQueueType;
	}

	public void setModelTaskQueueType(String modelTaskQueueType) {
		this.modelTaskQueueType = modelTaskQueueType;
	}

	public GetterSetter<T> getGetterSetter() {
		return this.getterSetter;
	}

	public void setGetterSetter(GetterSetter<T> getterSetter) {
		this.getterSetter = getterSetter;
	}

	public TestModelGenerator<T> getModelGenerator() {
		return this.modelGenerator;
	}

	public void setModelGenerator(TestModelGenerator<T> modelGenerator) {
		this.modelGenerator = modelGenerator;
	}

	public ScheduleDeleteTask<T> getDeleteTask() {
		return this.deleteTask;
	}

	public void setDeleteTask(ScheduleDeleteTask<T> deleteTask) {
		this.deleteTask = deleteTask;
	}

	public TaskQueueEditController getController() {
		return this.controller;
	}

	public void setController(TaskQueueEditController controller) {
		this.controller = controller;
	}

	// Make
	public List<T> create(boolean related) {
		List<T> models = this.modelGenerator.generate(this.genCount);

		for (T model : models) {
			if (related) {
				this.createRelated(model);
			} else {
				this.removeRelated(model, this.getterSetter);
			}
		}

		return models;
	}

	/**
	 * Removes all related components from the model, then saves it.
	 *
	 * @param model
	 * @param setter
	 */
	protected void removeRelated(T model,
	                             Setter<T> setter) {

	}

	/**
	 * Creates all related models and links them together.
	 *
	 * @param model
	 */
	protected void createRelated(T model) {
		// Nothing to create by default.
	}

	// Tests
	@Test
	public void testCreateFunction() {
		List<T> models = this.create(false);
		List<String> keys = ModelKey.readStringKeys(models);

		this.controller.reviewCreate(this.modelTaskQueueType, keys);

		Assert.assertTrue(this.isProperlyInitialized(models));
	}

	protected final boolean isProperlyInitialized(List<T> models) {
		boolean success = true;

		for (T model : models) {
			if (success == false) {
				break;
			}

			success = this.isProperlyInitialized(model);
		}

		return success;
	}

	protected boolean isProperlyInitialized(T model) {
		return true;
	}

	@Test
	public void testEditFunction() {
		List<T> models = this.create(true);
		List<String> keys = ModelKey.readStringKeys(models);

		// Complete Initializing Models
		this.controller.reviewCreate(this.modelTaskQueueType, keys);

		Assert.assertTrue(this.isProperlyInitialized(models));

		this.controller.reviewUpdate(this.modelTaskQueueType, keys);
	}

	@Test
	public final void testDeleteFunction() {
		List<T> models = this.create(true);
		this.testDelete(models);
	}

	public void testDelete(List<T> models) {
		List<String> keys = ModelKey.readStringKeys(models);

		// Complete Initializing Models
		this.controller.reviewCreate(this.modelTaskQueueType, keys);

		Assert.assertTrue(this.isProperlyInitialized(models));

		this.controller.processDelete(this.modelTaskQueueType, keys);

		Assert.assertTrue(this.isProperlyDeleted(models));
	}

	@Test
	public void testDeleteScheduling() {
		List<T> models = this.create(true);
		List<TaskRequest> requests = this.deleteTask.buildRequests(models);

		Assert.assertNotNull(requests);

		for (TaskRequest request : requests) {
			Assert.assertNotNull(request.getPath());
			Assert.assertTrue(request.getMethod() == Method.DELETE);

			Collection<TaskParameter> parameters = request.getParameters();
			Assert.assertFalse(parameters.isEmpty());
		}

	}

	protected final boolean isProperlyDeleted(List<T> models) {
		boolean success = (this.getterSetter.allExist(ModelKey.readModelKeys(models)) == false);

		for (T model : models) {
			if (success == false) {
				break;
			}

			success = this.isProperlyDeleted(model);
		}

		return success;
	}

	/**
	 * Checks the model's values to see whether or not it has been properly
	 * deleted.
	 * <p>
	 * Override.
	 */
	protected boolean isProperlyDeleted(T model) {
		return true;
	}

}