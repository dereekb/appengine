package com.dereekb.gae.model.extension.inclusion.service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.extension.inclusion.exception.InclusionTypeUnavailableException;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link InclusionService} response.
 *
 * @author dereekb
 *
 * @param <T>
 *            Primary type
 */
@Deprecated
public interface InclusionResponse<T> {

	/**
	 * Returns the set of all available related types.
	 *
	 * @return {@link Set} containing all available types.
	 */
	public Set<String> getAvailableTypes();

	/**
	 * Returns a single related type.
	 *
	 * @param type
	 *            related type
	 * @return {@link Collection} of related models. Never {@code null}.
	 * @throws InclusionTypeUnavailableException
	 *             thrown if the type was not requested to load.
	 */
	public Collection<? extends UniqueModel> getRelated(String type) throws InclusionTypeUnavailableException;

	/**
	 * Returns a map of all related models, keyed by type.
	 *
	 * @return {@link Map} containing all available related models.
	 */
	public Map<String, Collection<? extends UniqueModel>> getAllRelated();

}
