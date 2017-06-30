package com.dereekb.gae.model.extension.links.system.mutable;

import com.dereekb.gae.model.extension.links.system.components.Link;
import com.dereekb.gae.model.extension.links.system.mutable.exception.MutableLinkChangeException;

/**
 * Mutable {@link Link}.
 * 
 * @author dereekb
 *
 */
public interface MutableLink
        extends Link {

	/**
	 * Performs a link change.
	 * 
	 * @param change
	 *            {@link MutableLinkChange}. Never {@code null}.
	 * @return {@link MutableLinkChangeResult}. Never {@code null}.
	 * @throws MutableLinkChangeException
	 *             if the change fails.
	 */
	public MutableLinkChangeResult modifyKeys(MutableLinkChange change) throws MutableLinkChangeException;

}
