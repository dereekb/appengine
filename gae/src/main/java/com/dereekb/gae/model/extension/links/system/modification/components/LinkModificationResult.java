package com.dereekb.gae.model.extension.links.system.modification.components;

import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeResult;

/**
 * Wraps the {@link MutableLinkChangeResult} of a {@link LinkModification}
 * change.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationResult {

	/**
	 * Whether or not the model was modified.
	 */
	public boolean isModelModified();

	/**
	 * Whether or not the change was performed successfully.
	 * 
	 * @return {@code true} if successful.
	 */
	public boolean isSuccessful();

	/**
	 * Returns the original modification request.
	 * 
	 * @return {@link LinkModification}. Never {@code null}.
	 */
	public LinkModification getLinkModification();

	/**
	 * 
	 * @return {@link MutableLinkChangeResult}. Never {@code null} if
	 *         {{@link #isSuccessful()} returns {@code true}.
	 */
	public MutableLinkChangeResult getLinkChangeResult();

}
