package com.dereekb.gae.model.extension.links.components.system;

import java.util.Collection;
import java.util.Set;

import com.dereekb.gae.model.extension.links.deprecated.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.deprecated.components.system.exception.UnregisteredLinkTypeException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * System responsible for loading {@link LinkModelSet}.
 *
 * @author dereekb
 *
 */
@Deprecated
public interface LinkSystem {

	/**
	 * Returns a {@link Set} containing all available types.
	 *
	 * @return {@link Set} with all types. Never {@code null}.
	 */
	public Set<String> getAvailableSetTypes();

	/**
	 * Loads a {@link LinkModelSet} with the given type.
	 *
	 * @param type
	 *            Type to load.
	 * @return {@link LinkModelSet} instance. Never null.
	 * @throws {@link
	 *             UnregisteredLinkTypeException} if the requested type is
	 *             unavailable.
	 */
	@Deprecated
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
	 * @throws {@link
	 *             UnregisteredLinkTypeException} thrown if the requested
	 *             type is unavailable.
	 */
	@Deprecated
	public LinkModelSet loadSet(String type,
	                            Collection<ModelKey> keys)
	        throws UnregisteredLinkTypeException;

}
