package com.dereekb.gae.model.extension.links.system.modification;

/**
 * {@link LinkModificationSystem} entry that generates a
 * {@link LinkModificationSystemEntryInstance}.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemEntry {

	/**
	 * Generates a new instance of changes.
	 * 
	 * @return {@link LinkModificationSystemDelegateInstance}. Never
	 *         {@code null}.
	 */
	public LinkModificationSystemEntryInstance makeInstance();

}
