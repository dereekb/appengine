package com.dereekb.gae.model.extension.links.system.components;

import com.dereekb.gae.model.extension.links.system.components.exceptions.NoRelationException;

/**
 * Link information for links with a {@link LinkType#DYNAMIC} link type.
 * 
 * @author dereekb
 *
 */
public interface DynamicLinkInfo
        extends LinkAccessor {

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
	 */
	public Relation getRelationInfo() throws NoRelationException;

}
