package com.dereekb.gae.test.applications.api.model.tests.crud.core;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;

import com.dereekb.gae.model.crud.services.components.UpdateService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.request.impl.UpdateRequestImpl;
import com.dereekb.gae.model.crud.services.request.options.impl.UpdateRequestOptionsImpl;
import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.test.runnable.RunnableTest;
import com.dereekb.gae.utilities.collections.IteratorUtility;

/**
 * Used for testing a {@link UpdateService}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class UpdateServiceTester<T extends UniqueModel>
        implements RunnableTest {

	private Integer genCount = 3;

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

		this.testUpdatingMultiple(results);
		this.testUpdatingSingle(results.get(0));
		this.testUpdatingUnavailable();
		this.testUpdatingNothing();
	}

	private void testUpdatingSingle(T template) {
		UpdateRequest<T> request = new UpdateRequestImpl<T>(template);
		UpdateResponse<T> response = this.updateService.update(request);

		Collection<T> updated = response.getModels();
		Assert.assertTrue(updated.size() == 1);

		T updatedModel = updated.iterator().next();
		ModelKey updatedModelKey = updatedModel.getModelKey();
		template.getModelKey().equals(updatedModelKey);
	}

	private void testUpdatingMultiple(List<T> templates) {
		UpdateRequest<T> request = new UpdateRequestImpl<T>(templates);
		UpdateResponse<T> response = this.updateService.update(request);

		Collection<T> updated = response.getModels();
		Assert.assertTrue(updated.size() == this.genCount);
	}

	private void testUpdatingUnavailable() {
		this.testUpdatingAtomicUnavailable();
		this.testUpdatingNonAtomicUnavailable();
	}

	private void testUpdatingAtomicUnavailable() {
		T template = this.modelGenerator.generate();
		ModelKey key = template.getModelKey();
		this.getterSetter.delete(template, false);

		UpdateRequest<T> request = new UpdateRequestImpl<T>(template);

		try {
			this.updateService.update(request);
			Assert.fail("Should have failed atomic operation.");
		} catch (AtomicOperationException e) {
			Iterable<? extends UniqueModel> failed = e.getFailed();
			Assert.assertTrue(IteratorUtility.iterableToSet(failed).contains(key));
		}
	}

	private void testUpdatingNonAtomicUnavailable() {
		T template = this.modelGenerator.generate();
		this.getterSetter.delete(template, false);

		UpdateRequestOptionsImpl options = new UpdateRequestOptionsImpl(false);
		UpdateRequest<T> request = new UpdateRequestImpl<T>(template, options);

		try {
			UpdateResponse<T> response = this.updateService.update(request);
			Assert.assertTrue(response.getModels().size() == 0);
		} catch (AtomicOperationException e) {
			Assert.fail("Should not have failed atomic operation.");
		}
	}

	private void testUpdatingNothing() {
		List<T> templates = Collections.emptyList();
		UpdateRequest<T> request = new UpdateRequestImpl<T>(templates);
		UpdateResponse<T> response = this.updateService.update(request);

		Assert.assertTrue(response.getModels().isEmpty());
	}

}
