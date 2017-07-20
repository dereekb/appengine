package com.dereekb.gae.model.extension.links.system.mutable;

/**
 * {@link MutableLinkChange} type.
 * 
 * @author dereekb
 *
 */
public enum MutableLinkChangeType {

	ADD,

	REMOVE,

	SET,

	CLEAR,
	
	/**
	 * Special change type used in cases where a change must be submitted but no actual changes should take place. 
	 */
	NONE

}
