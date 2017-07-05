package com.dereekb.gae.model.extension.links.system.components;

import com.dereekb.gae.model.extension.links.system.components.exceptions.DynamicLinkInfoException;

/**
 * Abstracted link information.
 * 
 * @author dereekb
 * 
 * @see LimitedLinkInfo
 */
public interface AbstractedLinkInfo {

	/**
	 * Returns the link name.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getLinkName();

	/**
	 * Returns the type of link.
	 * 
	 * @return {@link LinkType}. Never {@code null}.
	 */
	public LinkType getLinkType();

	/**
	 * Returns the associated link type.
	 * 
	 * @return {@link String}. Never {@code null}.
	 * @throws DynamicLinkInfoException
	 *             if {@link #getLinkType()} return {@link LinkType#DYNAMIC}.
	 */
	public String getRelationLinkType() throws DynamicLinkInfoException;

	/**
	 * @return {@link LinkSize}. Never {@code null}.
	 */
	public LinkSize getLinkSize();

}
