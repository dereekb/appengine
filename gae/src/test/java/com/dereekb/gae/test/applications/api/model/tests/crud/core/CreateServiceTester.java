package com.dereekb.gae.test.applications.api.model.tests.crud.core;

import java.util.Collection;
import java.util.List;

import org.junit.Assert;

import com.dereekb.gae.model.crud.services.components.CreateService;
import com.dereekb.gae.model.crud.services.request.impl.CreateRequestImpl;
import com.dereekb.gae.model.crud.services.response.CreateResponse;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.test.runnable.RunnableTest;

/**
 * Used for testing a {@link CreateService}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class CreateServiceTester<T extends UniqueModel>
        implements RunnableTest {

	private Integer genCount = 5;

	protected Getter<T> getter;
	protected CreateService<T> createService;
	protected TestModelGenerator<T> modelGenerator;

	public CreateServiceTester(CreateService<T> createService, Getter<T> getter, TestModelGenerator<T> modelGenerator) {
		this.createService = createService;
		this.getter = getter;
		this.modelGenerator = modelGenerator;
	}

	public Integer getGenCount() {
		return this.genCount;
	}

	public void setGenCount(Integer genCount) {
		this.genCount = genCount;
	}

	public Getter<T> getGetter() {
		return this.getter;
	}

	public void setGetter(Getter<T> getter) {
		this.getter = getter;
	}

	public CreateService<T> getCreateService() {
		return this.createService;
	}

	public void setCreateService(CreateService<T> createService) {
		this.createService = createService;
	}

	public TestModelGenerator<T> getModelGenerator() {
		return this.modelGenerator;
	}

	public void setModelGenerator(TestModelGenerator<T> modelGenerator) {
		this.modelGenerator = modelGenerator;
	}

	@Override
	public void runTests() {
		this.testCreatingSingle();
		this.testCreatingMultiple();
		this.testCreatingNothing();
		this.testCreatingNull();
	}

	private void testCreatingSingle() {
		T template = this.modelGenerator.generate();
		CreateRequestImpl<T> request = new CreateRequestImpl<T>(template);
		CreateResponse<T> response = this.createService.create(request);

		Collection<ModelKey> keys = ModelKey.readModelKeys(response.getModels());
		Collection<T> created = response.getModels();

		Assert.assertTrue(keys.size() == 1);
		Assert.assertTrue(created.size() == 1);
	}

	private void testCreatingMultiple() {
		List<T> templates = this.modelGenerator.generate(this.genCount);
		CreateRequestImpl<T> request = new CreateRequestImpl<T>(templates);
		CreateResponse<T> response = this.createService.create(request);

		Collection<ModelKey> keys = ModelKey.readModelKeys(response.getModels());
		Collection<T> created = response.getModels();
		Assert.assertTrue(keys.size() == templates.size());
		Assert.assertTrue(created.size() == templates.size());
	}

	private void testCreatingNothing() {
		// TODO Auto-generated method stub

	}

	private void testCreatingNull() {
		// TODO Auto-generated method stub

	}

}