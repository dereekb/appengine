package com.dereekb.gae.model.extension.links.system.mutable;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Thread-safe service for reading {@link MutableLinkModel} instances.
 * 
 * @author dereekb
 */
public interface MutableLinkModelAccessor<T extends UniqueModel> {

	/**
	 * Reads existing models and returns a response containing available keys.
	 * 
	 * @param modelKeys {@link Collection}. Never {@code null}.
	 * @return {@link ReadResponse}. Never {@code null}.
	 */
	public ReadResponse<ModelKey> readExistingModels(Collection<ModelKey> modelKeys);
	
	/**
	 * Reads models and wraps them together with their pairs.
	 */
	public ReadResponse<? extends MutableLinkModelAccessorPair<T>> readMutableLinkModels(ReadRequest request)
	        throws AtomicOperationException;

}
