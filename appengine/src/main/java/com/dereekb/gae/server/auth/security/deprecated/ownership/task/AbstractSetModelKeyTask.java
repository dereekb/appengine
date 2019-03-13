package com.dereekb.gae.server.auth.security.ownership.task;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.impl.AbstractIterableTask;

/**
 * {@link IterableTask} implementation for setting ownership.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractSetModelKeyTask<T> extends AbstractIterableTask<T> {

	private ModelKey userKey;

	public AbstractSetModelKeyTask(ModelKey userKey) {
		this.setUserKey(userKey);
	}

	public ModelKey getUserKey() {
		return this.userKey;
	}

	public void setUserKey(ModelKey userKey) {
		if (userKey == null) {
			throw new IllegalArgumentException("userKey cannot be null.");
		}

		this.userKey = userKey;
	}

	// MARK: IterableTask
	@Override
	protected void doTaskOnObject(T input) {
		this.setKeyOnModel(input, this.userKey);
	}

	protected abstract void setKeyOnModel(T input,
	                                      ModelKey userKey);

	@Override
	public String toString() {
		return "SetAccountTask [userKey=" + this.userKey + "]";
	}

}
