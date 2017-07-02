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
	 * <p>
	 * If the change has already been applied, nothing will happen.
	 * 
	 * @return {@link LinkModificationResult}.
	 */
	public LinkModificationResult applyChange();

	/**
	 * Reverts the change on the model.
	 * <p>
	 * If {@link #applyChange()} has not yet been called, nothing will happen.
	 */
	public void undoChange();

}
