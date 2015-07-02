package com.dereekb.gae.model.extension.search.document.index.service;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.components.AtomicReadService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.search.document.SearchableUniqueModel;
import com.dereekb.gae.model.extension.search.document.index.IndexAction;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Default implementation of {@link UniqueKeyDocumentIndexService}
 *
 * Uses a {@link Getter} to retrieve models, then uses another
 * {@link DocumentIndexService} to perform the changes.
 *
 * @author dereekb
 */
public class UniqueModelDocumentIndexService<T extends SearchableUniqueModel>
        implements UniqueKeyDocumentIndexService {

	private final AtomicReadService<T> readService;
	private final DocumentIndexService<T> indexingService;

	public UniqueModelDocumentIndexService(AtomicReadService<T> readService, DocumentIndexService<T> indexingService) {
		this.readService = readService;
		this.indexingService = indexingService;
	}

	@Override
	public boolean indexChangeWithKeys(Collection<ModelKey> keys,
	                                   IndexAction action) throws AtomicOperationException {
		Collection<T> models = this.readService.read(keys);
		return this.indexingService.indexChange(models, action);
	}

	public AtomicReadService<T> getReadService() {
		return this.readService;
	}

    public DocumentIndexService<T> getIndexingService() {
		return this.indexingService;
	}
}
