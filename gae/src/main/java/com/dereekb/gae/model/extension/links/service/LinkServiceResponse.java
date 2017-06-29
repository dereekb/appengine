package com.dereekb.gae.model.extension.links.service;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMapWithSet;
import com.dereekb.gae.utilities.collections.map.HashMapWithSet;

/**
 * Response returned from a {@link LinkService}.
 * 
 * @author dereekb
 *
 */
public interface LinkServiceResponse {

	/**
	 * Returns a map containing all missing primary keys.
	 * <p>
	 * Sets are keyed by model type.
	 *
	 * @return {@link HashMapWithSet}. Never {@code null}.
	 */
	public CaseInsensitiveMapWithSet<ModelKey> getMissingPrimaryKeysSet();

}
