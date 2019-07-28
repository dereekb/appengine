package com.dereekb.gae.test.app.server.datastore.objectify;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.dereekb.gae.model.extension.generation.ModelGenerator;
import com.dereekb.gae.server.datastore.Deleter;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.Setter;
import com.dereekb.gae.server.datastore.Storer;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.exception.StoreKeyedEntityException;
import com.dereekb.gae.server.datastore.exception.UpdateUnkeyedEntityException;
import com.dereekb.gae.server.datastore.models.MutableUniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.MutableObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;

/**
 * Tests the
 *
 * @author dereekb
 *
 * @param <T>
 */
public class SetterTestUtility<T extends MutableUniqueModel> {

	private Getter<T> getter;
	private Setter<T> setter;
	private SetterTestUtilityDelegate<T> delegate;

	public SetterTestUtility(GetterSetter<T> getterSetter, SetterTestUtilityDelegate<T> delegate) {
		this(getterSetter, getterSetter, delegate);
	}

	public SetterTestUtility(Getter<T> getter, Setter<T> setter, SetterTestUtilityDelegate<T> delegate) {
		this.setGetter(getter);
		this.setSetter(setter);
		this.setDelegate(delegate);
	}

	public static <T extends MutableObjectifyModel<T>> SetterTestUtility<T> makeTestUtility(ObjectifyRegistry<T> registry,
	                                                                                        ModelGenerator<T> generator) {
		return new SetterTestUtility<T>(registry, new TestModelGeneratorSetterTestUtilityDelegateImpl<T>(generator));
	}

	public Getter<T> getGetter() {
		return this.getter;
	}

	public void setGetter(Getter<T> getter) {
		if (getter == null) {
			throw new IllegalArgumentException("getter cannot be null.");
		}

		this.getter = getter;
	}

	public Setter<T> getSetter() {
		return this.setter;
	}

	public void setSetter(Setter<T> setter) {
		if (setter == null) {
			throw new IllegalArgumentException("setter cannot be null.");
		}

		this.setter = setter;
	}

	public SetterTestUtilityDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(SetterTestUtilityDelegate<T> delegate) {
		if (delegate == null) {
			throw new IllegalArgumentException("delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	public void assertTestsPass() {
		this.assertStorerTestsPass();
		this.assertUpdaterTestsPass();
		this.assertDeleterTestsPass();
	}

	// MARK: Storer
	public void assertStorerTestsPass() {
		Storer<T> storer = this.setter;

		// Test Storing New
		T template = this.delegate.makeNew();
		template.setModelKey(null);	// Make sure it has no key set.

		assertTrue(template.getModelKey() == null);

		// Store It
		storer.store(template);
		assertFalse(template.getModelKey() == null);

		// Test It Was Stored
		assertTrue(this.getter.exists(template));

		// Test Storing existing fails.
		try {
			storer.store(template);
			fail();
		} catch (StoreKeyedEntityException e) {

		}

		// Test Storing with ID fails
		ModelKey key = template.getModelKey();
		template = this.delegate.makeNew();
		template.setModelKey(key);

		try {
			storer.store(template);
			fail();
		} catch (StoreKeyedEntityException e) {

		}

	}

	// MARK: Updater
	public void assertUpdaterTestsPass() {
		Storer<T> storer = this.setter;
		Updater<T> updater = this.setter;
		Deleter<T> deleter = this.setter;

		T template = this.delegate.makeNew();
		template.setModelKey(null);	// Make sure it has no key set.

		// Test Updating un-initiated fails.
		try {
			updater.update(template);
			fail();
		} catch (UpdateUnkeyedEntityException e) {

		}

		// Test Updating stored object.
		storer.store(template);
		updater.update(template);

		// Test Updating deleted object does nothing.
		deleter.delete(template);

		assertFalse(this.getter.exists(template));

		updater.update(template);

		assertFalse(this.getter.exists(template), "Should still not exist.");
	}

	// MARK: Deleter
	public void assertDeleterTestsPass() {
		Storer<T> storer = this.setter;

		Deleter<T> deleter = this.setter;

		ModelKey key = this.delegate.makeKey();

		// Test deleting nothing.
		deleter.deleteWithKey(key);

		// Test deleting something
		T template = this.delegate.makeNew();
		template.setModelKey(null);	// Make sure it has no key set.

		storer.store(template);

		assertTrue(this.getter.exists(template));
		deleter.delete(template);

		assertFalse(this.getter.exists(template), "Should not exist.");

	}

	public interface SetterTestUtilityDelegate<T> {

		public T makeNew();

		public ModelKey makeKey();

	}

	public static class TestModelGeneratorSetterTestUtilityDelegateImpl<T extends MutableUniqueModel>
	        implements SetterTestUtilityDelegate<T> {

		private ModelGenerator<T> generator;

		public TestModelGeneratorSetterTestUtilityDelegateImpl(ModelGenerator<T> generator) {
			super();
			this.setGenerator(generator);
		}

		public ModelGenerator<T> getGenerator() {
			return this.generator;
		}

		public void setGenerator(ModelGenerator<T> generator) {
			if (generator == null) {
				throw new IllegalArgumentException("generator cannot be null.");
			}

			this.generator = generator;
		}

		// MARK: SetterTestUtilityDelegate
		@Override
		public T makeNew() {
			return this.generator.generate();
		}

		@Override
		public ModelKey makeKey() {
			return this.generator.generateKey();
		}

	}

}
