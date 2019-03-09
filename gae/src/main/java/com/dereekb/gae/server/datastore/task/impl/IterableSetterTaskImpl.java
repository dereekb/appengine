package com.dereekb.gae.server.datastore.task.impl;

import com.dereekb.gae.server.datastore.Setter;
import com.dereekb.gae.server.datastore.task.IterableSetterTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link IterableSetterTask} implementation.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class IterableSetterTaskImpl<T>
        implements IterableSetterTask<T> {

	private Setter<T> setter;

	public IterableSetterTaskImpl(Setter<T> setter) {
		this.setSetter(setter);
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

	// MARK: IterableSetterTask
	@Override
	public void doStoreTask(Iterable<T> input) throws FailedTaskException {
		this.setter.store(input);
	}

	@Override
	public void doUpdateTask(Iterable<T> input) throws FailedTaskException {
		this.setter.update(input);
	}

	@Override
	public void doDeleteTask(Iterable<T> input) throws FailedTaskException {
		this.setter.delete(input);
	}

	@Override
	public String toString() {
		return "IterableSetterTaskImpl [setter=" + this.setter + "]";
	}

}
