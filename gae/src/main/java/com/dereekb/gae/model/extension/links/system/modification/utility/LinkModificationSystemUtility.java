package com.dereekb.gae.model.extension.links.system.modification.utility;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequestReference;
import com.dereekb.gae.model.extension.links.system.modification.exception.internal.ChangesAlreadyExecutedException;
import com.dereekb.gae.model.extension.links.system.modification.exception.request.ConflictingLinkModificationSystemRequestException;
import com.dereekb.gae.model.extension.links.system.modification.exception.request.InvalidLinkModificationSystemRequestException;

public class LinkModificationSystemUtility {

	public static List<LinkModificationSystemRequest> readRequests(Iterable<? extends LinkModificationSystemRequestReference> references) {
		List<LinkModificationSystemRequest> list = new ArrayList<LinkModificationSystemRequest>();

		for (LinkModificationSystemRequestReference reference : references) {
			list.add(reference.getRequest());
		}

		return list;
	}

	/**
	 * Queues all input requests with the instance.
	 * 
	 * @param requests
	 *            {@link Iterable}. Never {@code null}.
	 * @param instance
	 *            {@link LinkModificationSystemInstance}. Never {@code null}.
	 *            
	 * @throws UnavailableLinkException
	 * @throws UnavailableLinkModelException
	 * @throws ChangesAlreadyExecutedException
	 * @throws ConflictingLinkModificationSystemRequestException
	 * @throws InvalidLinkModificationSystemRequestException
	 */
	public static void queueRequests(Iterable<LinkModificationSystemRequest> requests,
	                                 LinkModificationSystemInstance instance)
	        throws UnavailableLinkException,
	            UnavailableLinkModelException,
	            ChangesAlreadyExecutedException,
	            ConflictingLinkModificationSystemRequestException,
	            InvalidLinkModificationSystemRequestException {
		for (LinkModificationSystemRequest request : requests) {
			instance.queueRequest(request);
		}
	}

}
