package com.dereekb.gae.model.extension.links.system.components;

import com.dereekb.gae.model.extension.links.components.model.LinkModel;

/**
 * Limited link information.
 * 
 * @author dereekb
 * 
 * @see LinkInfo for a full link.
 */
public interface LimitedLinkInfo
        extends AbstractedLinkInfo {

	/**
	 * Returns the model that is associated with this link.
	 * 
	 * @return {@link LinkModel}. Never {@code null}.
	 */
	public LimitedLinkModelInfo getLinkModelInfo();

}
