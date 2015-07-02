package com.dereekb.gae.model.extension.inclusion.service;

import java.util.Collection;
import java.util.Map;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link InclusionService} response.
 *
 * @author dereekb
 *
 * @param <T>
 *            Primary type
 */
public interface InclusionResponse<T> {

	/**
	 *
	 * @return a map of all related models.
	 */
	public Map<String, Collection<? extends UniqueModel>> getRelated();

	/**
	 *
	 * @return a map containing {@link Collection} instances of keys
	 *         corresponding to unavailable elements.
	 */
	public Map<String, Collection<ModelKey>> getUnavailable();

}
