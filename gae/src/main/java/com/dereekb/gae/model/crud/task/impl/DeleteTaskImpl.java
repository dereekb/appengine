package com.dereekb.gae.model.crud.task.impl;

import java.util.Collection;

import com.dereekb.gae.model.crud.exception.CancelDeleteException;
import com.dereekb.gae.model.crud.pairs.DeletePair;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.task.DeleteTask;
import com.dereekb.gae.model.crud.task.config.DeleteTaskConfig;
import com.dereekb.gae.model.crud.task.config.impl.DeleteTaskConfigImpl;
import com.dereekb.gae.model.crud.task.impl.delegate.DeleteTaskDelegate;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;
import com.dereekb.gae.utilities.collections.pairs.SuccessResultsPair;

/**
 * {@link DeleteTask} implementation. Uses a {@link Setter} to delete models,
 * after a {@link DeleteTaskDelegate} is used to prepare the models to be
 * deleted.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class DeleteTaskImpl<T extends UniqueModel>
        implements DeleteTask<T> {

	private ConfiguredSetter<T> setter;
	private DeleteTaskDelegate<T> delegate;
	private DeleteTaskConfig defaultConfig;

	public DeleteTaskImpl(ConfiguredSetter<T> setter) {
		this(setter, null);
	}

	public DeleteTaskImpl(ConfiguredSetter<T> setter, DeleteTaskDelegate<T> delegate) {
		this(setter, delegate, new DeleteTaskConfigImpl());
    }

	public DeleteTaskImpl(ConfiguredSetter<T> setter, DeleteTaskDelegate<T> delegate, DeleteTaskConfig defaultConfig) {
		this.setter = setter;
		this.delegate = delegate;
		this.defaultConfig = defaultConfig;
	}

	public ConfiguredSetter<T> getSetter() {
		return this.setter;
	}

	public void setSetter(ConfiguredSetter<T> setter) {
		this.setter = setter;
	}

	public DeleteTaskDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(DeleteTaskDelegate<T> delegate) {
		this.delegate = delegate;
	}

	public DeleteTaskConfig getDefaultConfig() {
		return this.defaultConfig;
	}

	public void setDefaultConfig(DeleteTaskConfig defaultConfig) {
		this.defaultConfig = defaultConfig;
	}

	// MARK: DeleteTask
	@Override
	public void doTask(Iterable<DeletePair<T>> input) {
		this.doTask(input, this.defaultConfig);
	}

	@Override
	public void doTask(Iterable<DeletePair<T>> input,
	                   DeleteTaskConfig configuration) {
		Collection<T> objects = DeletePair.getSources(input);

		try {
			// Prepare objects to be deleted.
			if (this.delegate != null) {
				this.delegate.deleteObjects(objects);
			}

			// Delete objects
			this.setter.delete(objects);

			// Update results states
			SuccessResultsPair.setResultPairsSuccess(input, true);
		} catch (CancelDeleteException e) {
			throw new AtomicOperationException(e.getRejected(), e);
		}
	}

	@Override
	public String toString() {
		return "DeleteTaskImpl [setter=" + this.setter + ", delegate=" + this.delegate + ", defaultConfig="
		        + this.defaultConfig + "]";
	}

}
