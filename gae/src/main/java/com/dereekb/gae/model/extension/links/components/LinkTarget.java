package com.dereekb.gae.model.extension.links.components;

import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * Describes a class in the Link system. Wraps that class's string name and
 * {@link ModelKeyType}.
 *
 * @author dereekb
 */
public interface LinkTarget {

	/**
	 * Returns the link's target type.
	 *
	 * @return the link's target type. Never null.
	 */
	public String getTargetType();

	/**
	 * Describes the type of keys expected.
	 *
	 * @return the {@link ModelKeyType} expected. {@link ModelKeyType#NAME},
	 *         {@link ModelKeyType#NUMBER}, or {@link ModelKeyType#DYNAMIC}.
	 */
	public ModelKeyType getTargetKeyType();

}
