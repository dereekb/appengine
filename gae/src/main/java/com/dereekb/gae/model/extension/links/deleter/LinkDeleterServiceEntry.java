package com.dereekb.gae.model.extension.links.deleter;

import java.util.Collection;
import java.util.Map;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Describes how to handle a delete request, and provides mechanisms for
 * deleting.
 *
 * @author dereekb
 *
 */
public interface LinkDeleterServiceEntry {

	/**
	 * Returns the model type this entry describes.
	 *
	 * @return model type. Never {@code null}.
	 */
	public String getModelType();

	/**
	 * Returns a {@link Map} of {@link LinkDeleterChangeType} values keyed by
	 * the name of the link to modify.
	 *
	 * @return {@link Map}. Never {@code null}.
	 */
	public Map<String, LinkDeleterChangeType> getChangesMap();

	/**
	 * Processes a delete on the specified models.
	 *
	 * @param keys
	 *            Keys of models to deleted. Never {@code null}.
	 */
	public void deleteModels(Collection<ModelKey> keys);

}
