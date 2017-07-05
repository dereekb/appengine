package com.dereekb.gae.model.extension.links.system.mutable;

import com.dereekb.gae.model.extension.links.system.components.DynamicLinkInfoAccessor;
import com.dereekb.gae.model.extension.links.system.components.LinkAccessor;
import com.dereekb.gae.model.extension.links.system.mutable.exception.MutableLinkChangeException;

/**
 * {@link LinkAccessor} that can modify the keys.
 * 
 * @author dereekb
 *
 */
public interface MutableLinkAccessor
        extends DynamicLinkInfoAccessor, LinkAccessor {

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
