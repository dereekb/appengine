package com.dereekb.gae.model.extension.search.document.search.service.model;

import com.dereekb.gae.model.extension.search.document.search.service.DocumentSearchService;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Search service built on top of a {@link DocumentSearchService} used to
 * retrieve models.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model Type
 * @param <Q>
 *            Query Type
 */
public interface ModelDocumentSearchService<T extends UniqueModel, Q> {

	public ModelDocumentSearchResponse<T> searchModels(Q query);

}
