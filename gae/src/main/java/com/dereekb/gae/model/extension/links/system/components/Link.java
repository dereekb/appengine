package com.dereekb.gae.model.extension.links.system.components;

import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * A physical link on an existing model that can be read for relationship
 * information.
 * 
 * @author dereekb
 *
 */
public interface Link {

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

	/**
	 * Returns the set of all linked keys.
	 * 
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<ModelKey> getLinkedModelKeys();

}
