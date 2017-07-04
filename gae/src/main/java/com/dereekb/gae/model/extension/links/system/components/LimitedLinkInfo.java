package com.dereekb.gae.model.extension.links.system.components;

import com.dereekb.gae.model.extension.links.components.model.LinkModel;

/**
 * Limited link information.
 * 
 * @author dereekb
 * 
 * @see LinkInfo for a full link.
 */
public interface LimitedLinkInfo {

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
	 */
	public String getLinkType();

	/**
	 * @return {@link LinkSize}. Never {@code null}.
	 */
	public LinkSize getLinkSize();

	/**
	 * Returns the model that is associated with this link.
	 * 
	 * @return {@link LinkModel}. Never {@code null}.
	 */
	public LimitedLinkModelInfo getLinkModelInfo();

}
