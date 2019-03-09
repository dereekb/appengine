package com.dereekb.gae.model.extension.links.service;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.HashMapWithSet;


public interface LinkServiceResponse {

	/**
	 * Returns a map containing all missing keys.
	 * <p>
	 * Sets are keyed by model type.
	 *
	 * @return {@link HashMapWithSet}. Never {@code null}.
	 */
	public HashMapWithSet<String, ModelKey> getMissingKeys();

}
