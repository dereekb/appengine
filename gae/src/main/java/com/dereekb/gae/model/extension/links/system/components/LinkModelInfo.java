package com.dereekb.gae.model.extension.links.system.components;

import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;

/**
 * Information about an abstract link model type.
 * 
 * @author dereekb
 * 
 * @see LinkModel for an instance that represents an existing model.
 */
public interface LinkModelInfo
        extends LimitedLinkModelInfo {

	/**
	 * Returns the {@link LinkInfo} for the requested link.
	 * 
	 * @param linkName
	 *            {@link String}. Never {@code null}.
	 * @return {@link LinkInfo}. Never {@code null}.
	 * @throws UnavailableLinkException
	 *             thrown if the requested link does not exist.
	 */
	public LinkInfo getLinkInfo(String linkName) throws UnavailableLinkException;

}
