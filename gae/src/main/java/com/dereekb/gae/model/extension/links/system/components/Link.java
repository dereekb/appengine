package com.dereekb.gae.model.extension.links.system.components;

/**
 * A physical link on an existing model that can be read for relationship
 * information.
 * 
 * @author dereekb
 *
 */
public interface Link
        extends DynamicLinkInfoAccessor, LinkAccessor {

	/**
	 * Returns meta data about this link.
	 * 
	 * @return {@link LinkInfo}. Never {@code null}.
	 */
	public LinkInfo getLinkInfo();

	/**
	 * Returns the associated link model.
	 * 
	 * @return {@link LinkModel}. Never {@code null}.
	 */
	public LinkModel getLinkModel();

}
