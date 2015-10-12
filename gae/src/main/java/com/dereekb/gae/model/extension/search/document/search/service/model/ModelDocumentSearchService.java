package com.dereekb.gae.model.extension.search.document.search.service.model;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.search.service.SearchDocumentService;

/**
 * Search service built on top of a {@link SearchDocumentService} used to
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
