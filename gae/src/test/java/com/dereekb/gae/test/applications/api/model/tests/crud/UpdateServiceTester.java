package com.dereekb.gae.test.applications.api.model.tests.crud;

import java.util.Collection;
import java.util.List;

import org.junit.Assert;

import com.dereekb.gae.model.crud.services.components.UpdateService;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.request.impl.UpdateRequestImpl;
import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.test.runnable.RunnableTest;

/**
 * Used for testing a {@link UpdateService}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class UpdateServiceTester<T extends UniqueModel>
        implements RunnableTest {

	private Integer genCount = 5;

	protected UpdateService<T> updateService;
	protected GetterSetter<T> getterSetter;
	protected TestModelGenerator<T> modelGenerator;

	public UpdateServiceTester(UpdateService<T> updateService,
	        GetterSetter<T> getterSetter,
	        TestModelGenerator<T> modelGenerator) {
		this.updateService = updateService;
		this.getterSetter = getterSetter;
		this.modelGenerator = modelGenerator;
	}

	public UpdateService<T> getUpdateService() {
		return this.updateService;
	}

	public GetterSetter<T> getGetterSetter() {
		return this.getterSetter;
	}

	public TestModelGenerator<T> getModelGenerator() {
		return this.modelGenerator;
	}

	@Override
	public void runTests() {
		List<T> results = this.modelGenerator.generate(this.genCount);
		List<ModelKey> keys = ModelKey.readModelKeys(results);

		this.testUpdatingSingle(keys.get(0));
		this.testUpdatingMultiple(keys);
		this.testUpdatingUnavailable();
		this.testUpdatingNothing();
	}

	private void testUpdatingSingle(ModelKey modelKey) {
		T template = this.modelGenerator.generate();
		ModelKey targetKey = template.getModelKey();

		UpdateRequest<T> request = new UpdateRequestImpl<T>(template);
		UpdateResponse<T> response = this.updateService.update(request);

		Collection<T> updated = response.getUpdatedModels();
		Assert.assertTrue(updated.size() == 1);

		T updatedModel = updated.iterator().next();
		ModelKey updatedModelKey = updatedModel.getModelKey();
		targetKey.equals(updatedModelKey);
	}

	private void testUpdatingMultiple(List<ModelKey> keys) {
		// TODO Auto-generated method stub

	}

	private void testUpdatingUnavailable() {
		// TODO Auto-generated method stub

	}

	private void testUpdatingNothing() {
		// TODO Auto-generated method stub

	}

}
