package com.dereekb.gae.model.extension.links.system.modification;

/**
 * {@link LinkModificationSystemChangeInstance} for
 * {@link LinkModificationSystemEntry}.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemEntryInstance {
	
	/**
	 * Applies changes to {@link LinkModificationPair} values that have state {@link LinkModificationPairState#INIT}.
	 */
	public void applyChanges();

	/**
	 * Commits changes to {@link LinkModificationPair} values that have state {@link LinkModificationPairState#DONE}.
	 */
	public void commitChanges();

	/**
	 * Reverts changes to {@link LinkModificationPair} values that have state {@link LinkModificationPairState#SUCCESS}.
	 */
	public void undoChanges();
	
}
