package com.dereekb.gae.model.extension.links.system.components;

import com.dereekb.gae.model.extension.links.components.model.LinkModel;

/**
 * A reference on a type to another type.
 * 
 * @author dereekb
 *
 * @see Link for an instance that represents an existing model's link.
 */
public interface LinkInfo {

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
	public LinkModelInfo getLinkModelInfo();

}
