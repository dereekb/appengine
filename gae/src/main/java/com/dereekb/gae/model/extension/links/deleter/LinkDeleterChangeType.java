package com.dereekb.gae.model.extension.links.deleter;


/**
 * Different changes possible when modifying a link.
 * 
 * @author dereekb
 *
 */
public enum LinkDeleterChangeType {

	/**
	 * Will attempt to load and delete the model that is linked.
	 */
	DELETE,

	/**
	 * Will attempt to load and unlink the model that is linked.
	 */
	UNLINK,

	/**
	 * Will do nothing.
	 */
	NONE

}
