package com.dereekb.gae.model.extension.links.components;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Contains information about a {@link Link}.
 *
 * @author dereekb
 *
 */
public interface LinkInfo {

	/**
	 * Returns the link's name. May be the same as the value
	 * {@link #getTargetType()} returns.
	 *
	 * The name is what is used to resolve links for requests.
	 *
	 * @return the link's name. Never null.
	 */
	public String getLinkName();

	/**
	 * Returns a {@link ModelKey} corresponding to the owner of this link..
	 *
	 * @return {@link ModelKey} for the parent model. Never null.
	 */
	public ModelKey getLinkModelKey();

	/**
	 * Returns a {@link LinkTarget} corresponding to the target type.
	 *
	 * @return {@link LinkTarget}. Never null.
	 */
	public LinkTarget getLinkTarget();

}
