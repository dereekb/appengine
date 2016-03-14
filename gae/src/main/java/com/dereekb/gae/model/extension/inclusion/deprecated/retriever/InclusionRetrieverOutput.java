package com.dereekb.gae.model.extension.inclusion.retriever;

import java.util.Collection;
import java.util.Map;

import com.dereekb.gae.model.extension.inclusion.exception.InclusionTypeUnavailableException;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Interface for wrapping {@link InclusionRetriever} output.
 *
 * @author dereekb
 */
@Deprecated
public interface InclusionRetrieverOutput {

	/**
	 * Returns all related models.
	 *
	 * @return all related models.
	 */
	public Map<String, Collection<? extends UniqueModel>> getAllRelatedModels();

	/**
	 * Returns the related models of a specific type.
	 *
	 * @param type
	 *            Related type to retrieve.
	 * @return {@link Collection} of related models.
	 * @throws InclusionTypeUnavailableException
	 *             if the type is not related.
	 */
	public Collection<? extends UniqueModel> getRelatedModels(String type) throws InclusionTypeUnavailableException;

}
