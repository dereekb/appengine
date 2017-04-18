package com.dereekb.gae.test.applications.api.model.tests.database;

import org.junit.Assert;

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

		Assert.assertTrue(template.getModelKey() == null);

		storer.store(template);

		Assert.assertFalse(template.getModelKey() == null);

		// Test Storing existing fails.
		try {
			storer.store(template);
			Assert.fail();
		} catch (StoreKeyedEntityException e) {

		}

		// Test Storing with ID fails
		ModelKey key = template.getModelKey();
		template = this.delegate.makeNew();
		template.setModelKey(key);

		try {
			storer.store(template);
			Assert.fail();
		} catch (StoreKeyedEntityException e) {

		}
	}

	// MARK: Updater
	public void assertUpdaterTestsPass() {
		Storer<T> storer = this.setter;
		Updater<T> updater = this.setter;
		Deleter<T> deleter = this.setter;

		T template = this.delegate.makeNew();

		// Test Updating un-initiated fails.
		try {
			updater.update(template);
			Assert.fail();
		} catch (UpdateUnkeyedEntityException e) {

		}

		// Test Updating stored object.
		storer.store(template);
		updater.update(template);

		// Test Updating deleted object does nothing.
		deleter.delete(template);

		Assert.assertFalse(this.getter.exists(template));

		updater.update(template);

		Assert.assertFalse("Should still not exist.", this.getter.exists(template));
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
		storer.store(template);

		Assert.assertTrue(this.getter.exists(template));
		deleter.delete(template);

		Assert.assertFalse("Should not exist.", this.getter.exists(template));

	}

	public interface SetterTestUtilityDelegate<T> {

		public T makeNew();

		public ModelKey makeKey();

	}

}
