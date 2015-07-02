package com.dereekb.gae.model.extension.links.components.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.components.system.LinkSystem;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Contains a set of {@link LinkModel} instances read from the
 * {@link LinkSystem}.
 *
 * This object can also load additional models into it if necessary.
 *
 * @author dereekb
 */
public interface LinkModelSet {

	/**
	 * @return {@link Set} of all model keys loaded in this set.
	 */
	public Set<ModelKey> getModelKeys();

	/**
	 * @return {@link List} of all {@link LinkModel} instances loaded in the
	 *         set.
	 *
	 * @see {@link #getMissingLinkModels()} for retrieving missing models.
	 */
	public Collection<LinkModel> getLinkModels();

	/**
	 * @param key
	 *            {@link ModelKey} of model to load.
	 * @return The link model, if it has been loaded and is in this set. Null if
	 *         it is not in the set.
	 */
	public LinkModel getModelForKey(ModelKey key);

	/**
	 * @param keys
	 *            collection of {@link ModelKey} of models to load.
	 * @return List of available {@link LinkModel} instances that have been
	 *         loaded in this set. Never null.
	 */
	public List<LinkModel> getModelsForKeys(Collection<ModelKey> keys);

	/**
	 * Returns the list of {@link LinkModel} keys that are available.
	 *
	 * @return list of keys corresponding to the available models. Never null.
	 */
	public Set<ModelKey> getAvailableModelKeys();

	/**
	 * Returns the list of {@link LinkModel} keys that were missing from the
	 * request.
	 *
	 * @return list of keys corresponding to the missing models. Never null.
	 */
	public Set<ModelKey> getMissingModelKeys();

	/**
	 * Loads an additional model into this set.
	 *
	 * @param keys
	 *            Keys of the models to load.
	 */
	public void loadModels(Collection<ModelKey> keys);

	/**
	 * Saves all link changes.
	 */
	public void save();

}
