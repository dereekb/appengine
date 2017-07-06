package com.dereekb.gae.model.extension.links.system.components;

import com.dereekb.gae.model.extension.links.system.components.exceptions.NoRelationException;

/**
 * Dynamic information about a {@link DynamicLinkAccessor}.
 * 
 * @author dereekb
 *
 */
public interface DynamicLinkAccessorInfo {

	/**
	 * Returns the associated link type.
	 * 
	 * @return {@link String}. May be {@code null}.
	 */
	public String getRelationLinkType();

	/**
	 * Returns info for the other side of the link, if it is available.
	 * 
	 * @return {@link Relation}. Never {@code null}.
	 * @throws NoRelationException
	 *             If there is no relation available.
	 * @throws UnsupportedOperationException
	 *             thrown if the link does not have sufficient information for a
	 *             relation.
	 */
	public Relation getRelationInfo() throws UnsupportedOperationException, NoRelationException;

}
