package com.dereekb.gae.client.api.model.extension.link;

import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.model.extension.link.exception.ClientLinkServiceChangeException;
import com.dereekb.gae.model.extension.links.service.LinkService;
import com.dereekb.gae.model.extension.links.service.LinkServiceRequest;

/**
 * Client {@link LinkService}.
 * 
 * @author dereekb
 *
 */
public interface ClientLinkService {

	/**
	 * Performs link updates.
	 * 
	 * @param request
	 *            {@link LinkServiceRequest}. Never {@code null}.
	 * @return {@link ClientLinkServiceResponse}. Never {@code null}.
	 * 
	 * @throws ClientLinkServiceChangeException
	 *             thrown if the request fails due to an exception occuring
	 *             within the link changes.
	 * @throws ClientIllegalArgumentException
	 *             thrown if the request has an illegal argument.
	 * @throws ClientAtomicOperationException
	 *             thrown if one or more objects fail to be updated.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 */
	public ClientLinkServiceResponse updateLinks(LinkServiceRequest request)
	        throws ClientLinkServiceChangeException,
	            ClientIllegalArgumentException,
	            ClientAtomicOperationException,
	            ClientRequestFailureException;

}
