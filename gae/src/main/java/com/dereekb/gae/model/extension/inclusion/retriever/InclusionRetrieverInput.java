package com.dereekb.gae.model.extension.inclusion.retriever;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Input for a {@link InclusionRetriever}.
 *
 * @author dereekb
 *
 */
public interface InclusionRetrieverInput {

	/**
	 * Returns all target types requested by this input.
	 *
	 * @return {@link Set} containing all requested types. Never {@code null}.
	 */
	public Set<String> getTargetTypes();

	/**
	 * Returns all keys matching the input target type. The type should be
	 * included in {@link #getTargetTypes()}.
	 *
	 * @param type
	 *            target type value. Never {@code null}.
	 * @return {@link Set} containing all requested keys.
	 */
	public List<ModelKey> getTargetKeysForType(String type);

}
