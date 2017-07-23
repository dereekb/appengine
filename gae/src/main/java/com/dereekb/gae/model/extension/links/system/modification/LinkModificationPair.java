package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResult;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.misc.keyed.AlwaysKeyed;

/**
 * Pair for {@link LinkModification} and its result.
 *
 * @author dereekb
 *
 */
public interface LinkModificationPair extends AlwaysKeyed<ModelKey>, LinkModificationReference {
	
	/**
	 * Sets the result.
	 * 
	 * @param result {@link LinkModificationResult}. Never {@code null}.
	 */
	public void setLinkModificationResult(LinkModificationResult result);
	
}
