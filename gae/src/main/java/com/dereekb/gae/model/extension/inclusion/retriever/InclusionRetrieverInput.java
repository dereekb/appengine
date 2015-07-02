package com.dereekb.gae.model.extension.inclusion.retriever;

import java.util.Collection;
import java.util.Map;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Wraps input for a {@link InclusionRetriever}.
 *
 * @author dereekb
 */
public interface InclusionRetrieverInput {

	/**
	 * Returns a {@link Map} containing {@link Collection} instances of
	 * {@link ModelKey} elements corresponding to each element.
	 *
	 * @return a map of keys corresponding to the related elements to load,
	 *         keyed by string.
	 */
	public Map<String, Collection<ModelKey>> getTargetsMap();

}
