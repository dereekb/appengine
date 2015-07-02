package com.dereekb.gae.model.extension.inclusion.reader;

import java.util.Collection;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Single element analysis from a {@link InclusionReader}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface InclusionReaderAnalysis<T> {

	/**
	 * Returns the analyzed model.
	 *
	 * @return the analyzed model.
	 */
	public T getReadModel();

	/**
	 * Returns all related types for this model.
	 *
	 * @return A {@link Set} containing every available related type for this
	 *         model. Never null.
	 */
	public Set<String> getRelatedTypes();

	/**
	 *
	 * @param type
	 * @return
	 */
	public Collection<ModelKey> getKeysForType(String type);

}
