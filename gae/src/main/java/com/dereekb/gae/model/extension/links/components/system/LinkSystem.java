package com.dereekb.gae.model.extension.links.components.system;

import java.util.Collection;

import com.dereekb.gae.model.extension.links.components.model.LinkModel;
import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.components.system.exception.UnregisteredLinkTypeException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * System responsible for loading {@link LinkModel}
 *
 * @author dereekb
 *
 */
public interface LinkSystem {

	/**
	 * Loads a {@link LinkModelSet} with the given type.
	 *
	 * @param type
	 *            Type to load.
	 * @return {@link LinkModelSet} instance. Never null.
	 * @throw {@link UnregisteredLinkTypeException} if the requested type is
	 *        unavailable.
	 */
	public LinkModelSet loadSet(String type) throws UnregisteredLinkTypeException;

	/**
	 * Loads a {@link LinkModelSet} with the given type, and {@link ModelKey}
	 * instances for each type.
	 *
	 * @param type
	 *            Target type to load.
	 * @param keys
	 *            Keys to add load in the set.
	 * @return {@link LinkModelSet} instance. Never null.
	 * @throw {@link UnregisteredLinkTypeException} thrown if the requested type
	 *        is unavailable.
	 */
	public LinkModelSet loadSet(String type,
	                            Collection<ModelKey> keys) throws UnregisteredLinkTypeException;

}
