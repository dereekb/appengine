package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;

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
	 *
	 * @throws AtomicOperationException thrown if atomic and one or more models are unavailable.
	 */
	public void applyChanges() throws AtomicOperationException;

	/**
	 * Commits changes to {@link LinkModificationPair} values that have state {@link LinkModificationPairState#DONE}.
	 */
	public void commitChanges();

	/**
	 * Reverts changes to {@link LinkModificationPair} values that have state {@link LinkModificationPairState#SUCCESS}.
	 */
	public void undoChanges();

	/**
	 * Convenience function that calls one of the other functions.
	 * 
	 * @param changeType {@link LinkModificationChangeType}. Never {@code null}.
	 * 
	 * @throws AtomicOperationException thrown if atomic and one or more models are unavailable.
	 */
	public void runChanges(LinkModificationChangeType changeType) throws AtomicOperationException;
	
}
