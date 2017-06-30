package com.dereekb.gae.model.extension.links.system.components;

import java.util.Set;

import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;

/**
 * Information about an abstract link model type.
 * 
 * @author dereekb
 * 
 * @see LinkModel for an instance that represents an existing model.
 */
public interface LinkModelInfo {

	/**
	 * Returns the set of all links names.
	 * 
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<String> getLinkNames();

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
