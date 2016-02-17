package com.dereekb.gae.model.extension.search.document.index.service.impl;

import java.util.Collection;
import java.util.Map;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.search.document.index.IndexAction;
import com.dereekb.gae.model.extension.search.document.index.service.KeyedDocumentIndexService;
import com.dereekb.gae.model.extension.search.document.index.service.TypedDocumentIndexService;
import com.dereekb.gae.model.extension.search.document.index.service.exception.UnregisteredSearchTypeException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link TypedDocumentIndexService} implementation.
 * <p>
 * Uses a {@link Map} with {@link KeyedDocumentIndexService} elements.
 *
 * @author dereekb
 *
 */
public class TypedDocumentIndexServiceImpl
        implements TypedDocumentIndexService {

	private Map<String, KeyedDocumentIndexService> services;

	public TypedDocumentIndexServiceImpl() {}

	public TypedDocumentIndexServiceImpl(Map<String, KeyedDocumentIndexService> services) {
		this.services = services;
	}

	public Map<String, KeyedDocumentIndexService> getServices() {
		return this.services;
	}

	public void setServices(Map<String, KeyedDocumentIndexService> services) {
		this.services = services;
	}

	@Override
    public boolean canChangeIndexForType(String modelType,
                                         IndexAction action) {
		KeyedDocumentIndexService service = this.services.get(modelType);
		boolean canChange;

		if (service != null) {
			canChange = service.canPerformAction(action);
		} else {
			canChange = false;
		}

	    return canChange;
    }

	@Override
	public boolean changeIndexWithKeys(String modelType,
	                                   Collection<ModelKey> keys,
	                                   IndexAction action)
	        throws UnregisteredSearchTypeException,
	            AtomicOperationException {
		KeyedDocumentIndexService service = this.services.get(modelType);
		boolean success;

		if (service != null) {
			success = service.indexChangeWithKeys(keys, action);
		} else {
			throw new UnregisteredSearchTypeException();
		}

		return success;
	}

	@Override
	public String toString() {
		return "TypedDocumentIndexServiceImpl [services=" + this.services + "]";
	}

}
