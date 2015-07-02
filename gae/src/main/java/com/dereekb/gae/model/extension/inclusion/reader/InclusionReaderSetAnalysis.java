package com.dereekb.gae.model.extension.inclusion.reader;

import java.util.Collection;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Results returned by {@link InclusionReader}.
 *
 * @author dereekb
 *
 */
public interface InclusionReaderSetAnalysis<T> {

	/**
	 * Returns a specific result for the given model.
	 *
	 * @param model
	 * @return {@link InclusionReaderAnalysis} for the input model. Never
	 *         null.
	 */
	public InclusionReaderAnalysis<T> getResultForModel(T model);

	/**
	 * Returns the models that were analyzed for this result.
	 *
	 * @return {@link Collection} of analyzed models.
	 */
	public Collection<T> getReadModels();

	/**
	 * Returns all related types for every model.
	 *
	 * @return A {@link Set} containing every available related type. Never
	 *         null.
	 */
	public Set<String> getRelatedTypes();

	/**
	 * Returns all target keys for the specified type, from each of the models.
	 *
	 * @param type
	 *            Related type.
	 * @return {@link Set} of {@link ModelKey} consisting of all related keys in
	 *         all analyzed models.
	 */
	public Set<ModelKey> getKeysForType(String type);

}
