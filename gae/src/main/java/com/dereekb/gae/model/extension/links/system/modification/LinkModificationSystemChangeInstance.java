package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.modification.exception.ChangesAlreadyComittedException;
import com.dereekb.gae.model.extension.links.system.modification.exception.UndoChangesAlreadyExecutedException;

/**
 * Abstract interface that can mark all changes as completed, or queue up an
 * undo action.
 * 
 * @author dereekb
 *
 */
public abstract interface LinkModificationSystemChangeInstance {

	/**
	 * All changes made within this instance are committed.
	 * 
	 * @throws UndoChangesAlreadyExecutedException if {@link #undoChanges()} was already called.
	 * @throws ChangesAlreadyComittedException if {@link #commitChanges()} was already called.
	 */
	public void commitChanges() throws UndoChangesAlreadyExecutedException, ChangesAlreadyComittedException;

	/**
	 * All changes made within this instance are reverted.
	 * 
	 * @throws UndoChangesAlreadyExecutedException if {@link #undoChanges()} was already called.
	 * @throws ChangesAlreadyComittedException if {@link #commitChanges()} was already called.
	 */
	public void undoChanges() throws ChangesAlreadyComittedException, UndoChangesAlreadyExecutedException;

}
