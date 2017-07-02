package com.dereekb.gae.model.extension.links.system.mutable;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Thread-safe service for reading {@link MutableLinkModel} instances.
 * 
 * @author dereekb
 */
public interface MutableLinkModelAccessor<T extends UniqueModel> {

	/**
	 * Reads models and wraps them together with their pairs.
	 */
	public ReadResponse<? extends MutableLinkModelAccessorPair<T>> readMutableLinkModels(ReadRequest request)
	        throws AtomicOperationException;

}
