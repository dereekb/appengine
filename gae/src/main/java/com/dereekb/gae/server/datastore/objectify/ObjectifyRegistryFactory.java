package com.dereekb.gae.server.datastore.objectify;

/**
 * Used for generating {@link ObjectifyRegistry} instances.
 *
 * @author dereekb
 */
public interface ObjectifyRegistryFactory {

	/**
	 * Creates a new {@link ObjectifyRegistry} for the passed type.
	 *
	 * @param type
	 *            {@link Class} for the type.
	 * @return a new {@link ObjectifyRegistry} with the target class.
	 */
	public <T extends ObjectifyModel<T>> ObjectifyRegistry<T> makeRegistry(Class<T> type);

}
