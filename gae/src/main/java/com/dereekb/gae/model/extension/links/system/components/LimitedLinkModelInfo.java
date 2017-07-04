package com.dereekb.gae.model.extension.links.system.components;

import java.util.Set;

import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;

/**
 * Information about an abstract link model type.
 * 
 * @author dereekb
 *
 * @see LinkModelInfo for a full model that can return full {@link LinkInfo}.
 */
public interface LimitedLinkModelInfo
        extends TypedLinkSystemComponent {

	/**
	 * Returns the set of all links names.
	 * 
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<String> getLinkNames();

	/**
	 * Returns the {@link LimitedLinkInfo} for the requested link.
	 * 
	 * @param linkName
	 *            {@link String}. Never {@code null}.
	 * @return {@link LimitedLinkInfo}. Never {@code null}.
	 * @throws UnavailableLinkException
	 *             thrown if the requested link does not exist.
	 */
	public LimitedLinkInfo getLinkInfo(String linkName) throws UnavailableLinkException;

}