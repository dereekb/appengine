package com.dereekb.gae.model.extension.links.system.modification;

/**
 * {@link LinkModificationPairFailure} reason.
 * 
 * @author dereekb
 *
 */
public enum LinkModificationPairFailureReason {
	
	/**
	 * Special case used for pairs that haven't failed. 
	 */
	NONE,
	
	/**
	 * The model was unavailable.
	 */
	UNAVAILABLE_MODEL,
	
	/**
	 * An exception caused the failure.
	 */
	ERROR

}
