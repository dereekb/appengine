package com.dereekb.gae.server.auth.security.ownership.task;

import com.dereekb.gae.server.datastore.models.owner.MutableOwnedModel;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link IterableTask} implementation for setting ownership.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class SetOwnershipRolesTask<T extends MutableOwnedModel>
        implements IterableTask<T> {

	private String ownerId;

	public SetOwnershipRolesTask(String ownerId) {
		this.setOwnerId(ownerId);
	}

	public String getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(String ownerId) throws IllegalArgumentException {
		if (ownerId == null) {
			throw new IllegalArgumentException("ownerId cannot be null.");
		}

		this.ownerId = ownerId;
	}

	// MARK: IterableTask
	@Override
	public void doTask(Iterable<T> input) throws FailedTaskException {
		for (T model : input) {
			model.setOwnerId(this.ownerId);
		}
	}

	@Override
	public String toString() {
		return "SetOwnershipRolesTask [ownerId=" + this.ownerId + "]";
	}

}
