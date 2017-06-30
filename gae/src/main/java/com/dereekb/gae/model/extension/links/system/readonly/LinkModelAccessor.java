package com.dereekb.gae.model.extension.links.system.readonly;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.extension.links.system.components.LinkModel;
import com.dereekb.gae.model.extension.links.system.components.TypedLinkSystemComponent;

/**
 * Thread-safe service for reading {@link LinkModel} instances.
 * 
 * @author dereekb
 *
 */
public interface LinkModelAccessor
        extends TypedLinkSystemComponent {

	/**
	 * Loads a lost of {@link LinkModel} implementations for requested
	 * models.
	 * 
	 * @param keys
	 *            {@link ReadRequest}. Never {@code null}.
	 * @return {@link ReadResponse}. Never {@code null}.
	 * 
	 * @throws AtomicOperationException
	 *             Occurs when the request specifies "atomic" and not all
	 *             objects requested can be read.
	 */
	public ReadResponse<LinkModel> readLinkModels(ReadRequest request) throws AtomicOperationException;

}
