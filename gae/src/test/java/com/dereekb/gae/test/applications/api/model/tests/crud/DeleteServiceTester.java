package com.dereekb.gae.test.applications.api.model.tests.crud;

import java.util.Collection;
import java.util.List;

import org.junit.Assert;

import com.dereekb.gae.model.crud.services.components.DeleteService;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.request.impl.DeleteRequestImpl;
import com.dereekb.gae.model.crud.services.response.DeleteResponse;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.test.runnable.RunnableTest;

/**
 * Used for testing a {@link DeleteService}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class DeleteServiceTester<T extends UniqueModel>
        implements RunnableTest {

	private Integer genCount = 5;

	protected GetterSetter<T> getterSetter;
	protected DeleteService<T> deleteService;
	protected TestModelGenerator<T> modelGenerator;

	public DeleteServiceTester(DeleteService<T> deleteService,
	        GetterSetter<T> getterSetter,
	        TestModelGenerator<T> modelGenerator) {
		this.deleteService = deleteService;
		this.getterSetter = getterSetter;
		this.modelGenerator = modelGenerator;
	}

	public Integer getGenCount() {
		return this.genCount;
	}

	public void setGenCount(Integer genCount) {
		this.genCount = genCount;
	}

	public GetterSetter<T> getGetterSetter() {
		return this.getterSetter;
	}

	public void setGetterSetter(GetterSetter<T> getterSetter) {
		this.getterSetter = getterSetter;
	}

	public DeleteService<T> getDeleteService() {
		return this.deleteService;
	}

	public void setDeleteService(DeleteService<T> deleteService) {
		this.deleteService = deleteService;
	}

	public TestModelGenerator<T> getModelGenerator() {
		return this.modelGenerator;
	}

	public void setModelGenerator(TestModelGenerator<T> modelGenerator) {
		this.modelGenerator = modelGenerator;
	}

	@Override
	public void runTests() {
		this.testDeletingSingle();
		this.testDeletingMultiple();
		this.testDeletingNothing();
		this.testDeletingNull();
	}

	private void testDeletingSingle() {
		T target = this.modelGenerator.generate();
		ModelKey targetKey = target.getModelKey();

		DeleteRequest<T> request = new DeleteRequestImpl<T>(targetKey);
		DeleteResponse<T> response = this.deleteService.delete(request);

		Collection<T> deleted = response.getDeletedModels();
		Assert.assertTrue(deleted.size() == 1);

		T deletedModel = deleted.iterator().next();
		ModelKey deletedModelKey = deletedModel.getModelKey();
		targetKey.equals(deletedModelKey);

		T reread = this.getterSetter.get(deletedModel);
		Assert.assertTrue(reread == null);
	}

	private void testDeletingMultiple() {
		List<T> targets = this.modelGenerator.generate(this.genCount);
		List<ModelKey> targetKeys = ModelKey.readModelKeys(targets);

		DeleteRequest<T> request = new DeleteRequestImpl<T>(targetKeys);
		DeleteResponse<T> response = this.deleteService.delete(request);

		Collection<T> deleted = response.getDeletedModels();
		Assert.assertTrue(deleted.size() == targetKeys.size());

		List<T> reread = this.getterSetter.get(targets);
		Assert.assertTrue(reread.isEmpty());
	}

	private void testDeletingNothing() {
		// TODO Auto-generated method stub

	}

	private void testDeletingNull() {
		// TODO Auto-generated method stub

	}

}