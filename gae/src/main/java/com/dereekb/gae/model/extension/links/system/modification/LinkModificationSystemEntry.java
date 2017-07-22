package com.dereekb.gae.model.extension.links.system.modification;

import java.util.Set;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LinkModificationSystem} entry that generates a
 * {@link LinkModificationSystemEntryInstance}.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemEntry {

	/**
	 * Asserts that all models for the input keys are available.
	 * 
	 * @param keys {@link Set}. Never {@code null}.
	 * 
	 * @throws UnavailableModelException thrown if one or more models are unavailable.
	 */
	public void assertModelsExist(Set<ModelKey> keys) throws UnavailableModelException;
	
	/**
	 * Generates a new instance of changes.
	 * 
	 * @return {@link LinkModificationSystemDelegateInstance}. Never
	 *         {@code null}.
	 */
	public LinkModificationSystemEntryInstance makeInstance();


}
