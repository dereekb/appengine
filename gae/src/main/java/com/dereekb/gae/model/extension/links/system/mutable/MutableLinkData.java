package com.dereekb.gae.model.extension.links.system.mutable;

import com.dereekb.gae.model.extension.links.system.components.AbstractedLinkInfo;
import com.dereekb.gae.model.extension.links.system.components.LimitedLinkInfo;

/**
 * {@link AbstractedLinkInfo} that isn't directly associated with a link model
 * like {@link LimitedLinkInfo}.
 * 
 * @author dereekb
 *
 */
public interface MutableLinkData<T>
        extends AbstractedLinkInfo {

	/**
	 * Creates a new accessor for the link on the input model.
	 * 
	 * @param model
	 *            Model. Never {@code null}.
	 * @return {@link MutableLinkAccessor}. Never {@code null}.
	 */
	public MutableLinkAccessor makeLinkAccessor(T model);

}
