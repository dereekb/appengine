package com.dereekb.gae.server.datastore.models.keys;

/**
 * Describes a {@link ModelKey} instance's key type.
 *
 * @author dereekb
 *
 */
public enum ModelKeyType {

	/**
	 * Key for an uninitialized model.
	 */
	DEFAULT,

	/**
	 * Key has a {@link String} name.
	 */
	NAME,

	/**
	 * Key has a {@link Long} identifier.
	 */
	NUMBER,

	/**
	 * Is not for describing a specific key, but describes that keys from a
	 * DYNAMIC source may be either NAME or NUMBER types.
	 */
	@Deprecated
	DYNAMIC;

}
