package com.dereekb.gae.model.extension.links.system.modification;

import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.filters.FilterResults;

/**
 * {@link LinkModificationSystem} entry that generates a
 * {@link LinkModificationSystemEntryInstance}.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemEntry {

	/**
	 * Filters models for the the input keys that are available.
	 * 
	 * @param keys {@link Set}. Never {@code null}.
	 * 
	 * @return {@link FilterResults}. Never {@code null}.
	 */
	public FilterResults<ModelKey> filterModelsExist(Set<ModelKey> keys);

	/**
	 * Generates a new instance of changes.
	 * 
	 * @param config {@link LinkModificationSystemEntryInstanceConfig}. Never {@code null}.
	 * 
	 * @return {@link LinkModificationSystemDelegateInstance}. Never
	 *         {@code null}.
	 */
	public LinkModificationSystemEntryInstance makeInstance(LinkModificationSystemEntryInstanceConfig config);

}
