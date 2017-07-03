package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResult;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModel;

/**
 * A change instance for a single model that can applied and undone.
 * <p>
 * Should be used by a {@link LinkModificationSystemModelChangeInstanceSet}.
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
	 * @return {@link LinkModificationResult}. Never {@code null}.
	 */
	public LinkModificationResult applyChange();

	/**
	 * Reverts the change on the model.
	 * <p>
	 * If {@link #applyChange()} has not yet been called, nothing will happen.
	 * 
	 * @param linkModel
	 *            {@link MutableLinkModel}. Can be {@code null}.
	 * @return {@code true} if the model was modified.
	 */
	public boolean undoChange(MutableLinkModel linkModel);

}
