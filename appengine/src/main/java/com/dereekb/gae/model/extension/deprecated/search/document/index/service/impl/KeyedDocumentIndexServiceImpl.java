package com.dereekb.gae.model.extension.search.document.index.service.impl;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.components.AtomicReadService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.deprecated.search.document.SearchableUniqueModel;
import com.dereekb.gae.model.extension.deprecated.search.document.index.IndexAction;
import com.dereekb.gae.model.extension.deprecated.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.model.extension.deprecated.search.document.index.service.KeyedDocumentIndexService;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Implementation of {@link KeyedDocumentIndexService}
 *
 * Uses a {@link Getter} to retrieve models, then uses another
 * {@link DocumentIndexService} to perform the changes.
 *
 * @author dereekb
 */
public class KeyedDocumentIndexServiceImpl<T extends SearchableUniqueModel>
        implements KeyedDocumentIndexService {

	private AtomicReadService<T> readService;
	private DocumentIndexService<T> indexingService;

	public KeyedDocumentIndexServiceImpl() {}

	public KeyedDocumentIndexServiceImpl(AtomicReadService<T> readService, DocumentIndexService<T> indexingService) {
		this.readService = readService;
		this.indexingService = indexingService;
	}

	public AtomicReadService<T> getReadService() {
		return this.readService;
	}

	public void setReadService(AtomicReadService<T> readService) {
		this.readService = readService;
	}

	public DocumentIndexService<T> getIndexingService() {
		return this.indexingService;
	}

	public void setIndexingService(DocumentIndexService<T> indexingService) {
		this.indexingService = indexingService;
	}

	// MARK: KeyedDocumentIndexService
	@Override
	public boolean canPerformAction(IndexAction action) {
		return true;
	}

	@Override
	public void indexChangeWithKeys(Collection<ModelKey> keys,
	                                IndexAction action)
	        throws AtomicOperationException {
		Collection<T> models = this.readService.read(keys);
		this.indexingService.indexChange(models, action);

		// TODO: Save?
	}

	@Override
	public String toString() {
		return "KeyedDocumentIndexServiceImpl [readService=" + this.readService + ", indexingService="
		        + this.indexingService + "]";
	}

}
