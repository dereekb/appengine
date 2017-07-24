package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResult;
import com.dereekb.gae.model.extension.links.system.modification.exception.NoUndoChangesException;

/**
 * An idempotent change instance for a single model that can applied and undone.
 * <p>
 * Should be used by a {@link LinkModificationSystemModelChangeInstanceSet}.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemModelChangeInstance {

	/**
	 * Applies the change to the model. The internal {@link LinkModificationPair} is updated.
	 * <p>
	 * If the change has already been applied, nothing will happen.
	 * 
	 * @return {@link LinkModificationResult}. Never {@code null}.
	 */
	public LinkModificationResult applyChange();

	/**
	 * Reverts the change on the model. The internal {@link LinkModificationPair} is updated.
	 * <p>
	 * If a change has not yet occured, nothing will happen.
	 * 
	 * @return {@link LinkModificationResult}. Never {@code null}.
	 * @throws NoUndoChangesException thrown if no undo is applicable.
	 */
	public LinkModificationResult undoChange() throws NoUndoChangesException;

}
