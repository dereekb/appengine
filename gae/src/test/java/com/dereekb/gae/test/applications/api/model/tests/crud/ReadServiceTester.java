package com.dereekb.gae.test.applications.api.model.tests.crud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.request.impl.KeyReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.exception.NullModelKeyException;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.test.runnable.RunnableTest;

/**
 * Used for testing a {@link ReadService}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class ReadServiceTester<T extends UniqueModel>
        implements RunnableTest {

	private Integer genCount = 5;

	protected ReadService<T> readService;
	protected GetterSetter<T> getterSetter;
	protected TestModelGenerator<T> modelGenerator;

	public ReadServiceTester(ReadService<T> readService,
	        GetterSetter<T> getterSetter,
	        TestModelGenerator<T> modelGenerator) {
		this.readService = readService;
		this.getterSetter = getterSetter;
		this.modelGenerator = modelGenerator;
	}

	public ReadService<T> getReadService() {
		return this.readService;
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

		this.testReadingSingle(keys.get(0));
		this.testReadingMultiple(keys);
		this.testReadingNothing();
		this.testReadingNull();
		this.testReadingUnavailable();
	}

	/**
	 * Tests function service by searching with a list containing two of the
	 * input key.
	 *
	 * The expected result is a single item.
	 */
	private void testReadingSingle(ModelKey key) {

		// Options set w/o atomic read
		ReadRequestOptions options = new ReadRequestOptions(false);
		ReadRequest<T> request = new KeyReadRequest<T>(key, options);
		ReadResponse<T> response = this.readService.read(request);

		// Check we recieved one object back.
		Collection<T> readObjects = response.getModels();
		Assert.assertTrue(readObjects.size() == 1);

		// Check returned is the "expected".
		T first = readObjects.iterator().next();
		ModelKey firstId = first.getModelKey();
		Assert.assertTrue(key.equals(firstId));
	}

	/**
	 * Standard test of reading multiple items.
	 */
	private void testReadingMultiple(List<ModelKey> keys) {

		ReadRequestOptions options = new ReadRequestOptions(false);
		ReadRequest<T> request = new KeyReadRequest<T>(keys, options);
		ReadResponse<T> response = this.readService.read(request);

		Collection<T> readObjects = response.getModels();
		Collection<ModelKey> filtered = response.getFiltered();
		Collection<ModelKey> missing = response.getUnavailable();

		// Check we received the amount we expected
		Assert.assertTrue(readObjects.size() == keys.size());
		Assert.assertTrue(filtered.size() == 0);
		Assert.assertTrue(missing.size() == 0);
	}

	/**
	 * Tests reading nothing. The service should not fail, but also not return
	 * anything.
	 */
	private void testReadingNothing() {
		List<ModelKey> keys = new ArrayList<ModelKey>();

		ReadRequestOptions options = new ReadRequestOptions(false);
		ReadRequest<T> request = new KeyReadRequest<T>(keys, options);
		ReadResponse<T> response = this.readService.read(request);

		Collection<T> readObjects = response.getModels();
		Collection<ModelKey> filtered = response.getFiltered();
		Collection<ModelKey> missing = response.getUnavailable();

		Assert.assertTrue(readObjects.size() == 0);
		Assert.assertTrue(filtered.size() == 0);
		Assert.assertTrue(missing.size() == 0);
	}

	/**
	 * Tests reading an null/non-existant key.
	 */
	private void testReadingNull() {
		List<ModelKey> keys = new ArrayList<ModelKey>();
		keys.add(null);

		try {
			ReadRequestOptions options = new ReadRequestOptions(false);
			ReadRequest<T> request = new KeyReadRequest<T>(keys, options);
			this.readService.read(request);
			Assert.fail();
		} catch (NullModelKeyException e) {
			// Catch null keys.
		}
	}

	/**
	 * Test reading elements that have never been created.
	 */
	private void testReadingUnavailable() {

		List<T> tempModels = this.getModelGenerator().generate(3);
		List<ModelKey> keys = ModelKey.readModelKeys(tempModels);
		this.getterSetter.delete(tempModels, false);

		// Request is set to atomic read.
		try {
			// Atomic exception should be raised.
			ReadRequestOptions options = new ReadRequestOptions(true);
			ReadRequest<T> request = new KeyReadRequest<T>(keys, options);
			this.readService.read(request);

			Assert.fail();
		} catch (AtomicOperationException e) {
			List<ModelKey> unavailable = e.getUnavailableModelKeys();
			Assert.assertTrue(keys.size() == unavailable.size());
			Assert.assertTrue(keys.containsAll(unavailable));
		}

		// Request is set to not atomic.
		try {
			// Atomic exception should not be raised.
			ReadRequestOptions options = new ReadRequestOptions(false);
			ReadRequest<T> request = new KeyReadRequest<T>(keys, options);
			ReadResponse<T> response = this.readService.read(request);

			Collection<ModelKey> unavailable = response.getUnavailable();
			Assert.assertTrue(keys.size() == unavailable.size());
			Assert.assertTrue(keys.containsAll(unavailable));
		} catch (AtomicOperationException e) {
			Assert.fail();
		}

	}

}
