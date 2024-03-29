package com.dereekb.gae.model.extension.inclusion.reader;

import java.util.Collection;

import com.dereekb.gae.model.extension.inclusion.exception.InclusionTypeUnavailableException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMapWithSet;
import com.dereekb.gae.utilities.collections.set.CaseInsensitiveSet;

/**
 * Single element analysis from a {@link ModelInclusionReader} for a model.
 *
 * @author dereekb
 */
public interface InclusionReaderAnalysis {

	/**
	 * Returns the analyzed model's key.
	 *
	 * @return {@link ModelKey}. Never {@code null}.
	 */
	public ModelKey getModelKey();

	/**
	 * Returns all related types for this model type.
	 *
	 * @return A {@link CaseInsensitiveSet} containing every available
	 *         related type for this model type. Never {@code null}.
	 */
	public CaseInsensitiveSet getRelatedTypes();

	/**
	 * Returns all {@link ModelKey} values for the specified type.
	 *
	 * @param type
	 *            model type to load. Never {@code null}.
	 * @return {@link Collection} of all keys of the {@code type}.
	 * @throws InclusionTypeUnavailableException
	 *             thrown if the type is unavailable.
	 */
	public Collection<ModelKey> getKeysForType(String type) throws InclusionTypeUnavailableException;

	/**
	 * Returns a map of all types and keys.
	 *
	 * @return {@link CaseInsensitiveMapWithSet}. Never {@code null}.
	 */
	public CaseInsensitiveMapWithSet<ModelKey> getRelationMap();

}