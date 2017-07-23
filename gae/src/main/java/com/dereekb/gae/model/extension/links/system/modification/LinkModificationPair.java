package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.utilities.collections.pairs.SuccessPair;

/**
 * {@link SuccessPair} for {@link LinkModification}. 
 *
 * @author dereekb
 *
 */
public interface LinkModificationPair extends SuccessPair<LinkModification> {

	/**
	 * @return {@link LinkModification}. Never {@code null}.
	 */
	public LinkModification getModification();
	
	/**
	 * @return {@link LinkModificationSystemRequest}. Never {@code null}.
	 */
	public LinkModificationSystemRequest getRequest();
	
}
