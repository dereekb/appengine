package com.dereekb.gae.test.app.mock.server.datastore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.server.datastore.objectify.MutableObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.app.mock.client.tests.AbstractModelRequestSenderTests;
import com.dereekb.gae.test.app.mock.context.AbstractAppContextOnlyTestingContext;
import com.dereekb.gae.test.app.server.datastore.objectify.SetterTestUtility;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

/**
 * Abstract tests similar to {@link AbstractModelRequestSenderTests} that tests
 * CRUDs for a model.
 *
 * @author dereekb
 *
 * @param <T>
 */
public abstract class AbstractObjectifyModelCrudTest<T extends MutableObjectifyModel<T>> extends AbstractAppContextOnlyTestingContext {

	protected TestModelGenerator<T> testModelGenerator;
	protected ObjectifyRegistry<T> registry;

	protected transient SetterTestUtility<T> testUtility;

	public TestModelGenerator<T> getTestModelGenerator() {
		return this.testModelGenerator;
	}

	public void setTestModelGenerator(TestModelGenerator<T> testModelGenerator) {
		this.testModelGenerator = testModelGenerator;
	}

	public ObjectifyRegistry<T> getRegistry() {
		return this.registry;
	}

	public void setRegistry(ObjectifyRegistry<T> registry) {
		this.registry = registry;
	}

	// MARK: Utilities
	@BeforeEach
	public void setUtilities() {
		this.testUtility = SetterTestUtility.makeTestUtility(this.registry, this.testModelGenerator);
	}

	// MARK: CRUD
	@Test
	public void assertStorerTestsPass() {
		this.testUtility.assertStorerTestsPass();
	}

	@Test
	public void assertUpdaterTestsPass() {
		this.testUtility.assertUpdaterTestsPass();
	}

	@Test
	public void assertDeleterTestsPass() {
		this.testUtility.assertDeleterTestsPass();
	}

}
