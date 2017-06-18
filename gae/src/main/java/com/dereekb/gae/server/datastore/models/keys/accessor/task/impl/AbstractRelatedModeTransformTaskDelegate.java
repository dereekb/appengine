package com.dereekb.gae.server.datastore.models.keys.accessor.task.impl;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.task.ModelKeyListAccessorTransformTaskDelegate;

/**
 * Abstract {@link ModelKeyListAccessorTransformTaskDelegate} implementation.
 * 
 * @author dereekb
 *
 * @param <I>
 *            input model type
 * @param <O>
 *            output model type
 */
public abstract class AbstractRelatedModeTransformTaskDelegate<I extends UniqueModel, O extends UniqueModel>
        implements ModelKeyListAccessorTransformTaskDelegate<I, O> {

	// MARK: ModelKeyListAccessorTransformTaskDelegate
	@Override
	public abstract ModelKeyListAccessor<O> convertListAccessor(ModelKeyListAccessor<I> input);

	/**
	 * Abstract {@link ModelKeyListAccessor} implementation that performs
	 * simple conversions and caches the conversions.
	 * 
	 * @author dereekb
	 *
	 */
	protected abstract class AbstractModelKeyListAccessorImpl
	        implements ModelKeyListAccessor<O> {

		private List<ModelKey> convertedKeys = null;
		private List<O> loadedModels = null;

		private final ModelKeyListAccessor<I> input;

		public AbstractModelKeyListAccessorImpl(ModelKeyListAccessor<I> input) {
			this.input = input;
		}

		// MARK: ModelKeyListAccessor
		@Override
		public List<ModelKey> getModelKeys() {
			if (this.convertedKeys == null) {
				this.convertedKeys = this.convertKeys(this.input);
			}

			return this.convertedKeys;
		}

		@Override
		public List<O> getModels() {
			if (this.loadedModels == null) {
				this.loadedModels = this.loadModelsForKeys(this.getModelKeys());
			}

			return this.loadedModels;
		}

		protected abstract List<ModelKey> convertKeys(ModelKeyListAccessor<I> input);

		protected abstract List<O> loadModelsForKeys(List<ModelKey> keys);
	}

}
