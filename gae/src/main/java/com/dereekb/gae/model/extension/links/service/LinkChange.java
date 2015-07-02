package com.dereekb.gae.model.extension.links.service;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Represents a change in the system.
 *
 * @author dereekb
 *
 */
public interface LinkChange {

	/**
	 * @return {@link LinkChangeAction} to perform.
	 */
	public LinkChangeAction getAction();

	/**
	 * The type of the model to change.
	 *
	 * @return type of primary model.
	 */
	public String getPrimaryType();

	/**
	 * The {@link ModelKey} corresponding to the primary model to modify.
	 *
	 * For example, if we wanted to change a model that had the name "x", this
	 * would return a {@link ModelKey} with the name "x".
	 *
	 * @return {@link ModelKey} corresponding to the primary model to modify.
	 */
	public ModelKey getPrimaryKey();

	/**
	 * The name of the link in the primary model to change.
	 *
	 * @return name of the target {@link Link} to change.
	 */
	public String getLinkName();

	/**
	 * The keys to change.
	 *
	 * @return {@link ModelKey} collection corresponding to target models.
	 */
	public Collection<ModelKey> getTargetKeys();

}
