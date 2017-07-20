package com.dereekb.gae.model.extension.links.system.mutable;

import com.dereekb.gae.model.extension.links.system.components.Link;
import com.dereekb.gae.model.extension.links.system.mutable.impl.MutableLinkChangeImpl;

/**
 * Used for building undo modifications.
 * 
 * @author dereekb
 *
 */
public interface MutableLinkChangeUndoBuilder {

	/**
	 * Makes a reverse change for a previously executed
	 * {@link MutableLinkChange}, for the input {@link Link}.
	 * 
	 * @param link
	 *            {@link Link}. Never {@code null}.
	 * @param change
	 *            {@link MutableLinkChange}. Never {@code null}.
	 * @param result
	 *            {@link MutableLinkChangeResult}. Never {@code null}.
	 * @return {@link MutableLinkChangeImpl}. Never {@code null}.
	 */
	public MutableLinkChange makeUndo(Link currentLink,
	                                  MutableLinkChange previousChange,
	                                  MutableLinkChangeResult result);

}
