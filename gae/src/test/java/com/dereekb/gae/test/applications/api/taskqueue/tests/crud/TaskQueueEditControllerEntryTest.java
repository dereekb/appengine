package com.dereekb.gae.test.applications.api.taskqueue.tests.crud;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
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

	private Getter<T> getter;
	private TestModelGenerator<T> modelGenerator;
	private TaskQueueEditControllerEntry controllerEntry;

    public Integer getGenCount() {
		return this.genCount;
	}

	public void setGenCount(Integer genCount) {
		this.genCount = genCount;
	}

	public TestModelGenerator<T> getModelGenerator() {
		return this.modelGenerator;
	}

	public void setModelGenerator(TestModelGenerator<T> modelGenerator) {
		this.modelGenerator = modelGenerator;
	}

	public TaskQueueEditControllerEntry getControllerEntry() {
		return this.controllerEntry;
	}

	public void setControllerEntry(TaskQueueEditControllerEntry controllerEntry) {
		this.controllerEntry = controllerEntry;
	}

	// Tests
	@Test
	public void testCreateFunction() {
		List<T> models = this.modelGenerator.generate(this.genCount);
		List<ModelKey> keys = ModelKey.readModelKeys(models);

		this.controllerEntry.reviewCreate(keys);

		this.reviewCreationFunctionResults(models);
	}

	public abstract void reviewCreationFunctionResults(List<T> models);

	@Test
	public void testEditFunction() {
		List<T> models = this.modelGenerator.generate(this.genCount);
		List<ModelKey> keys = ModelKey.readModelKeys(models);

		this.controllerEntry.reviewUpdate(keys);

		this.reviewEditedFunctionResults(models);
	}

	public abstract void reviewEditedFunctionResults(List<T> models);

	@Test
	public void testDeleteFunction() {
		List<T> models = this.modelGenerator.generate(this.genCount);
		List<ModelKey> keys = ModelKey.readModelKeys(models);

		this.controllerEntry.processDelete(keys);

		Assert.assertFalse(this.getter.allExist(keys));

		this.reviewDeleteFunctionResults(models);
	}

	public abstract void reviewDeleteFunctionResults(List<T> models);

}
