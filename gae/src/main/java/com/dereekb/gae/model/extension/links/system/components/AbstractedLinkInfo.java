package com.dereekb.gae.model.extension.links.system.components;

/**
 * Abstracted link information.
 * 
 * @author dereekb
 * 
 * @see LimitedLinkInfo
 */
public interface AbstractedLinkInfo extends SimpleLinkInfo {

	/**
	 * Returns the type of link.
	 * 
	 * @return {@link LinkType}. Never {@code null}.
	 */
	public LinkType getLinkType();

	/**
	 * @return {@link LinkSize}. Never {@code null}.
	 */
	public LinkSize getLinkSize();

}
