package com.dereekb.gae.model.extension.links.system.modification.components;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationReference;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeResult;
import com.dereekb.gae.utilities.misc.success.SuccessModel;

/**
 * Wraps the {@link MutableLinkChangeResult} of a {@link LinkModification}
 * change.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationResult extends LinkModificationReference, SuccessModel {

	/**
	 * Whether or not the model was modified.
	 */
	public boolean isModelModified();

	/**
	 * Returns the result.
	 * 
	 * @return {@link MutableLinkChangeResult}. Never {@code null} if
	 *         {{@link #isSuccessful()} returns {@code true}.
	 */
	public MutableLinkChangeResult getLinkChangeResult();

}
