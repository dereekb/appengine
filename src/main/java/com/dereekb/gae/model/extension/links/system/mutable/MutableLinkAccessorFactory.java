package com.dereekb.gae.model.extension.links.system.mutable;

import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;

/**
 * Factory for a {@link MutableLinkAccessor}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface MutableLinkAccessorFactory<T> {

	/**
	 * Creates a new link accessor for the specified type.
	 * 
	 * @param linkName
	 *            {@link String}. Never {@code null}.
	 * @param model
	 *            Model. Never {@code null}.
	 * @return {@link MutableLinkAccessor}. Never {@code null}.
	 * @throws UnavailableLinkException
	 *             thrown if the link is unavailable
	 */
	public MutableLinkAccessor makeLinkAccessor(String linkName,
	                                            T model)
	        throws UnavailableLinkException;

}
