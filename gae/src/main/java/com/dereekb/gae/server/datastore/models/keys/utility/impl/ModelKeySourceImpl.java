package com.dereekb.gae.server.datastore.models.keys.utility.impl;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.exception.NoModelKeyException;
import com.dereekb.gae.server.datastore.models.keys.utility.ModelKeySource;
import com.dereekb.gae.utilities.model.lazy.exception.UnavailableSourceObjectException;
import com.dereekb.gae.utilities.model.source.Source;
import com.dereekb.gae.utilities.model.source.impl.UnavailableSourceImpl;

/**
 * {@link Source} factory for {@link ModelKeySource}.
 * 
 * @author dereekb
 *
 */
public final class ModelKeySourceImpl {

	public static ModelKeySource make(Long id) {
		ModelKey key = ModelKey.safe(id);
		return ModelKeySourceImpl.make(key);
	}

	public static ModelKeySource make(String id) {
		ModelKey key = ModelKey.safe(id);
		return ModelKeySourceImpl.make(key);
	}

	public static ModelKeySource make(ModelKey key) {
		if (key != null) {
			return new KeyInstance(key);
		} else {
			return new UnavailableInstance();
		}
	}

	// MARK: Instance
	private static class KeyInstance
	        implements ModelKeySource {

		private final ModelKey key;

		public KeyInstance(ModelKey key) {
			this.key = key;
		}

		// MARK: ModelKeySource
		@Override
		public ModelKey loadObject() throws RuntimeException, UnavailableSourceObjectException {
			return this.key;
		}

		@Override
		public ModelKey loadModelKey() throws NoModelKeyException {
			return this.key;
		}

	}

	private static class UnavailableInstance extends UnavailableSourceImpl<ModelKey>
	        implements ModelKeySource {

		// MARK: ModelKeySource
		@Override
		public ModelKey loadModelKey() throws NoModelKeyException {
			throw new NoModelKeyException();
		}

	}

}
