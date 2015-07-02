package com.thevisitcompany.gae.test.deprecated.models.support.registry;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.thevisitcompany.gae.server.datastore.models.DatabaseModel;
import com.thevisitcompany.gae.server.datastore.objectify.ObjectifyModel;
import com.thevisitcompany.gae.server.datastore.objectify.ObjectifyModelRegistry;

@Ignore
public abstract class AbstractModelRegistryTest<T extends ObjectifyModel<T>> {

	protected final ObjectifyModelRegistry<T> registry;
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	private Integer generationCount = 3;

	public AbstractModelRegistryTest(ObjectifyModelRegistry<T> registry) {
		this.registry = registry;
	}

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	/**
	 * Creates a new model with a null identifier.
	 * 
	 * @return
	 */
	protected abstract T generateModel();

	protected List<T> generateModels(Integer count) {
		List<T> models = new ArrayList<T>(count);

		for (int i = 0; i < models.size(); i += 1) {
			T newModel = this.generateModel();
			models.add(newModel);
		}

		return models;
	}

	protected void assertModelsHaveIdentifier(Iterable<T> models,
	                                          boolean hasId) {
		for (T model : models) {
			if (hasId) {
				Assert.assertNotNull(model.getId());
			} else {
				Assert.assertNull(model.getId());
			}
		}
	}

	protected List<T> generateAndSaveModels(Integer count,
	                                        boolean async) {
		List<T> models = this.generateModels(generationCount);

		try {
			registry.save(models, async);
		} catch (RuntimeException e) {
			e.printStackTrace();
			fail();
		}

		return models;
	}

	@Test
	public void testCreation() {
		T model = this.generateModel();
		Assert.assertNull(model.getId());

		try {
			registry.save(model, false);
		} catch (RuntimeException e) {
			e.printStackTrace();
			fail();
		}

		assertNotNull(model.getId());

		T secondModel = this.generateModel();
		registry.save(secondModel, false);

		T readModel = registry.get(model);
		assertNotNull(readModel.getId());
		assertTrue(readModel.modelEquals(model));
	}

	@Test
	public void testMultipleCreation() {
		List<T> models = this.generateModels(generationCount);
		this.assertModelsHaveIdentifier(models, false);

		try {
			registry.save(models, false);
		} catch (RuntimeException e) {
			e.printStackTrace();
			fail();
		}

		this.assertModelsHaveIdentifier(models, true);

		// Async Create models
		this.generateAndSaveModels(generationCount, true);
	}

	@Test
	public void testUpdate() {
		T model = this.generateModel();
		registry.save(model, false);

		assertNotNull(model.getId());

		registry.save(model, false);
	}

	@Test
	public void testDelete() {

		T model = this.generateModel();
		registry.save(model, false);

		Long identifier = model.getId();
		assertNotNull(identifier);

		registry.delete(model, true);
	}

	@Test
	public void testRead() {

		T model = this.generateModel();
		registry.save(model, false);

		Long identifier = model.getId();
		assertNotNull(identifier);

		T readModel = registry.get(identifier);

		assertNotNull(readModel);
		assertNotNull(readModel.getId());
		assertTrue(readModel.equals(model));
		assertTrue(readModel.modelEquals(model));
	}

	@Test
	public void testMultipleRead() {
		List<T> models = this.generateAndSaveModels(generationCount, false);

		List<Key<T>> keys = ObjectifyModel.readKeys(models);
		List<T> readWithKeys = this.registry.getWithKeys(keys);

		List<Long> identifiers = DatabaseModel.readModelIdentifiers(models);
		List<T> readWithIdentifiers = this.registry.getWithIds(identifiers);

		Assert.assertTrue(readWithKeys.containsAll(readWithIdentifiers));
		Assert.assertTrue(readWithIdentifiers.containsAll(readWithKeys));
	}

}