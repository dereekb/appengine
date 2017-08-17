package com.dereekb.gae.model.extension.links.system.components;

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
	 * @return {@link LimitedLinkModelInfo}. Never {@code null}.
	 */
	public LimitedLinkModelInfo getLinkModelInfo();

}
