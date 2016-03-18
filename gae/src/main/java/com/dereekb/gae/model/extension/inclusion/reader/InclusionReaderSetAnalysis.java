package com.dereekb.gae.model.extension.inclusion.reader;

import java.util.Collection;
import java.util.Set;

import com.dereekb.gae.model.extension.inclusion.exception.InclusionTypeUnavailableException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Results returned by {@link ModelInclusionReader}.
 *
 * @author dereekb
 *
 */
public interface InclusionReaderSetAnalysis {

	/**
	 * Returns the {@link ModelKey} values that were analyzed for this result.
	 *
	 * @return {@link Collection} of analyzed models.
	 */
	public Collection<ModelKey> getModelKeys();

	/**
	 * Returns all related types for the model type.
	 *
	 * @return A {@link Set} containing every available related type. Never
	 *         {@code null}.
	 */
	public Set<String> getRelatedTypes();

	/**
	 * Returns all target keys for the specified type from each of the models in
	 * this analysis.
	 *
	 * @param type
	 *            Related type.
	 * @return {@link Set} of {@link ModelKey} consisting of all related keys in
	 *         all analyzed models.
	 * @throws InclusionTypeUnavailableException
	 */
	public Set<ModelKey> getKeysForType(String type) throws InclusionTypeUnavailableException;

}
