package com.dereekb.gae.model.extension.links.system.modification;

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
	 * <p>
	 * Review tasks and related actions may be submitted within this period.
	 */
	public void commitChanges();

	/**
	 * All changes made within this instance are reverted.
	 */
	public void undoChanges();

}
