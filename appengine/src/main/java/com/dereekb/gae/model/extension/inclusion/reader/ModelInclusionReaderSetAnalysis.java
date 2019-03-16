package com.dereekb.gae.model.extension.inclusion.reader;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Results returned by {@link ModelInclusionReader}.
 *
 * @author dereekb
 *
 */
public interface ModelInclusionReaderSetAnalysis<T extends UniqueModel>
        extends InclusionReaderSetAnalysis {

	/**
	 * Returns the models that were analyzed for this result.
	 *
	 * @return {@link Collection} of analyzed models.
	 */
	public Collection<T> getAnalyzedModels();

}
