package com.dereekb.gae.model.crud.task.impl.delegate.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.crud.pairs.CreatePair;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.task.impl.delegate.CreateTaskValidator;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.IteratorUtility;
import com.dereekb.gae.utilities.misc.keyed.exception.NullKeyException;
import com.dereekb.gae.web.api.util.attribute.impl.InvalidAttributeImpl;

/**
 * {@link CreateTaskValidator} implementation for models with generated keys.
 * <p>
 * It ensures that no model already exists with any identifier, and also
 * prevents the same models in the request from having the same identifier.
 *
 * @author dereekb
 *
 */
public class IsUniqueCreateTaskValidatorImpl<T extends UniqueModel>
        implements CreateTaskValidator<T> {

	public static final String KEY_NOT_UNIQUE_CODE = "KEY_NOT_UNIQUE";
	public static final String MODEL_EXISTS_CODE = "MODEL_EXISTS";

	private Getter<T> getter;

	public IsUniqueCreateTaskValidatorImpl(Getter<T> getter) {
		this.setGetter(getter);
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

	// MARK: CreateTaskValidator
	@Override
	public void doTask(Iterable<CreatePair<T>> input) throws AtomicOperationException {
		new Instance(input).doTask();
	}

	private class Instance {

		private final List<CreatePair<T>> pairs;
		private final Set<ModelKey> keySet = new HashSet<ModelKey>();

		public Instance(Iterable<CreatePair<T>> input) {
			this.pairs = IteratorUtility.iterableToList(input);
		}

		public void doTask() {
			boolean failed = this.checkAllHaveUniqueKeys() || this.checkNoEntitiesAlreadyExist();

			if (failed) {
				throw new AtomicOperationException();
			}
		}

		private boolean checkAllHaveUniqueKeys() {
			boolean failed = false;

			for (CreatePair<T> pair : this.pairs) {
				ModelKey key = pair.keyValue();

				if (key == null) {
					throw new NullKeyException();
				} else if (this.keySet.contains(key)) {
					InvalidAttributeImpl attribute = new InvalidAttributeImpl("identifier", key.toString(),
					        "The key for this is not unique.", KEY_NOT_UNIQUE_CODE);
					pair.setAttributeFailure(attribute);
					failed = true;
				} else {
					this.keySet.add(key);
				}
			}

			return failed;
		}

		private boolean checkNoEntitiesAlreadyExist() {
			Set<ModelKey> existing = IsUniqueCreateTaskValidatorImpl.this.getter.getExisting(this.keySet);

			if (existing.isEmpty() == false) {
				Map<ModelKey, CreatePair<T>> map = ModelKey.makeModelKeyMap(this.pairs);

				for (ModelKey key : existing) {
					CreatePair<T> pair = map.get(key);

					InvalidAttributeImpl attribute = new InvalidAttributeImpl("identifier", key.toString(),
					        "A model already exists with this key.", MODEL_EXISTS_CODE);
					pair.setAttributeFailure(attribute);
				}

				return true;
			}

			return false;
		}

	}

}
