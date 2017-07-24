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
	 * Returns the pair state.
	 *  
	 * @return {@link LinkModificationPairState}. Never {@code null}.
	 */
	public LinkModificationPairState getState();

	/**
	 * Returns the current result, if it exists.
	 * 
	 * @return {@link LinkModificationResult}. Never {@code null}.
	 */
	public LinkModificationResult getLinkModificationResult();

	/**
	 * Sets the result.
	 * 
	 * @param result {@link LinkModificationResult}. Never {@code null}.
	 */
	public void setLinkModificationResult(LinkModificationResult result);

	/**
	 * Returns the undo result, if it exists.
	 * 
	 * @return {@link LinkModificationResult}. Never {@code null}.
	 */
	public LinkModificationResult getUndoResult();

	/**
	 * Sets the undo result.
	 * 
	 * @param result {@link LinkModificationResult}. Never {@code null}.
	 */
	public void setUndoResult(LinkModificationResult result);

}
