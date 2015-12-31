package com.dereekb.gae.test.applications.api.taskqueue.tests.crud;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.taskqueue.controller.crud.TaskQueueEditController;
import com.dereekb.gae.web.taskqueue.controller.crud.TaskQueueEditControllerEntry;

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

	private Getter<T> getter;
	private TestModelGenerator<T> modelGenerator;

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

	public Getter<T> getGetter() {
		return this.getter;
	}

	public void setGetter(Getter<T> getter) {
		this.getter = getter;
	}

	public TestModelGenerator<T> getModelGenerator() {
		return this.modelGenerator;
	}

	public void setModelGenerator(TestModelGenerator<T> modelGenerator) {
		this.modelGenerator = modelGenerator;
	}

	public TaskQueueEditController getController() {
		return this.controller;
	}

	public void setController(TaskQueueEditController controller) {
		this.controller = controller;
	}

	// Make
	private List<T> create(boolean related) {
		List<T> models = this.modelGenerator.generate(this.genCount);

		if (related) {
			for (T model : models) {
				this.createRelated(model);
			}
		}

		return models;
	}

	protected abstract void createRelated(T model);

	// Tests
	@Test
	public void testCreateFunction() {
		List<T> models = this.create(false);
		List<String> keys = ModelKey.readStringKeys(models);

		this.controller.reviewCreate(this.modelTaskQueueType, keys);
	}

	@Test
	public void testEditFunction() {
		List<T> models = this.create(true);
		List<String> keys = ModelKey.readStringKeys(models);

		// Complete Initializing Models
		this.controller.reviewCreate(this.modelTaskQueueType, keys);

		this.controller.reviewUpdate(this.modelTaskQueueType, keys);
	}

	@Test
	public void testDeleteFunction() {
		List<T> models = this.create(true);
		List<String> keys = ModelKey.readStringKeys(models);

		// Complete Initializing Models
		this.controller.reviewCreate(this.modelTaskQueueType, keys);

		this.controller.processDelete(this.modelTaskQueueType, keys);

		Assert.assertFalse(this.getter.allExist(ModelKey.readModelKeys(models)));
	}

}
