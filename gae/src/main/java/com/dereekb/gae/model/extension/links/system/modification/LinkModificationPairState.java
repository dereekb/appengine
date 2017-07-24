package com.dereekb.gae.model.extension.links.system.modification;

/**
 * {@link LinkModificationPair} state.
 * 
 * @author dereekb
 *
 */
public enum LinkModificationPairState {
	
	/**
	 * If the change has not yet been attempted.
	 */
	INIT,
	
	/**
	 * If the change was successful.
	 */
	SUCCESS,
	
	/**
	 * If this change failed.
	 */
	FAILED,
	
	/**
	 * If this change has been undone.
	 */
	UNDONE

}
