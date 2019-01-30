package com.dereekb.gae.model.extension.links.system.modification;


/**
 * Abstract interface that references a {@link LinkModificationSystemRequest}.
 * 
 * @author dereekb
 *
 */
public abstract interface LinkModificationSystemRequestReference {

	/**
	 * Returns the original request for this result.
	 * 
	 * @return {@link LinkModificationSystemRequest}. Never {@code null}.
	 */
	public LinkModificationSystemRequest getRequest();
	
}
