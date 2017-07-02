package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResult;

/**
 * A change instance for a single model that can be safely undone.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemModelChangeInstance {

	/**
	 * Applies the change to the model.
	 * 
	 * @return {@link LinkModificationResult}.
	 */
	public LinkModificationResult applyChange();

	/**
	 * Reverts the change on the model.
	 */
	public void undoChange();

}
