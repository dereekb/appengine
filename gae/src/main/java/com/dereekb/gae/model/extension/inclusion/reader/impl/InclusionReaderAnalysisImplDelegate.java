package com.dereekb.gae.model.extension.inclusion.reader.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.extension.inclusion.exception.InclusionTypeUnavailableException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link InclusionReaderAnalysisImpl} delegate.
 *
 * @author dereekb
 *
 */
public interface InclusionReaderAnalysisImplDelegate<T> {

	/**
	 * Returns all related types for this model type.
	 *
	 * @return A {@link Set} containing every available related type for this
	 *         model type. Never {@code null}.
	 */
	public Set<String> getRelatedTypes();

	/**
	 * Returns all keys for the input model.
	 *
	 * @param model
	 *            model to load keys from. Never {@code null}.
	 * @return {@link Map} containing {@link Set} values for each related type.
	 */
	public Map<String, Set<ModelKey>> getRelatedKeysMap(T model) throws InclusionTypeUnavailableException;

	/**
	 * Returns all {@link ModelKey} values for the specified type.
	 *
	 * @param type
	 *            model type to load. Never {@code null}.
	 * @param model
	 *            model to load keys from. Never {@code null}.
	 * @return {@link Collection} of all keys of the {@code type}.
	 * @throws InclusionTypeUnavailableException
	 *             thrown if the type is unavailable.
	 */
	public Collection<ModelKey> getRelatedKeysForType(String type,
	                                                  T model) throws InclusionTypeUnavailableException;

}
