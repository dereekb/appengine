package com.dereekb.gae.model.extension.links.system.components;

import com.dereekb.gae.model.extension.links.system.components.exceptions.DynamicLinkInfoException;

/**
 * Simple link information.
 * 
 * @author dereekb
 * 
 * @see AbstractedLinkInfo
 */
public interface SimpleLinkInfo {

	/**
	 * Returns the link name.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getLinkName();

	/**
	 * Returns the associated link type.
	 * 
	 * @return {@link String}. Never {@code null}.
	 * @throws DynamicLinkInfoException
	 *             if {@link #getLinkType()} return {@link LinkType#DYNAMIC}.
	 */
	public String getRelationLinkType() throws DynamicLinkInfoException;

}
