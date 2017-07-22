package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;
import com.dereekb.gae.model.extension.links.system.modification.exception.ConflictingLinkModificationSystemRequestException;
import com.dereekb.gae.model.extension.links.system.modification.exception.TooManyChangeKeysException;

/**
 * {@link LinkModificationSystemRequestValidator} instance.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemRequestValidatorInstance {

	/**
	 * Asserts that the request is valid.
	 * 
	 * @param request {@link LinkModificationSystemRequest}. Never {@code null}.
	 * @throws UnavailableLinkModelException
	 * @throws UnavailableLinkException
	 * @throws TooManyChangeKeysException
	 * @throws ConflictingLinkModificationSystemRequestException 
	 */
	public void validateRequest(LinkModificationSystemRequest request)
	        throws UnavailableLinkModelException,
	            UnavailableLinkException,
	            TooManyChangeKeysException, 
	            ConflictingLinkModificationSystemRequestException;
	
}
